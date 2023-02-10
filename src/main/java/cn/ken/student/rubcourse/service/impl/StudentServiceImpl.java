package cn.ken.student.rubcourse.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.common.util.StringUtils;
import cn.ken.student.rubcourse.common.util.ValidateCodeUtil;
import cn.ken.student.rubcourse.dto.req.StudentExcel;
import cn.ken.student.rubcourse.dto.req.StudentLoginReq;
import cn.ken.student.rubcourse.dto.req.StudentOnConditionReq;
import cn.ken.student.rubcourse.dto.sys.resp.StudentResp;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.entity.StudentCredits;
import cn.ken.student.rubcourse.listener.StudentExcelListener;
import cn.ken.student.rubcourse.mapper.ClassMapper;
import cn.ken.student.rubcourse.mapper.StudentCreditsMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IStudentService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;
    
    @Autowired
    private ClassMapper classMapper;
    
    @Autowired
    private StudentCreditsMapper studentCreditsMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public Result addStudent(HttpServletRequest httpServletRequest, Student student) throws Exception {
        Student selectById = studentMapper.selectById(student.getId());
        if (selectById != null) {
            throw new BusinessException(ErrorCodeEnums.STUDENT_EXISTS);
        }
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getId, student.getClassId());
        Class result = classMapper.selectOne(queryWrapper);
        if (result == null) {
            throw new BusinessException(ErrorCodeEnums.CLASS_NOT_EXISTS);
        }
        String salt = IdUtil.simpleUUID();
        student.setSalt(salt);
        String md5Password = DigestUtil.md5Hex(student.getPassword() + salt);
        student.setPassword(md5Password);
        studentMapper.insert(student);
        // 插入各个学期的学分
        String substring = student.getId().toString().substring(0, 4);
        for (int i=0; i<=3; i++) {
            int integer = Integer.parseInt(substring) + i;
            for (int j=1; j<=2; j++) {
                Integer semester = Integer.valueOf(integer + String.valueOf(j));
                StudentCredits studentCredits = new StudentCredits(SnowflakeUtil.nextId(), student.getId(), semester, BigDecimal.valueOf(22),  BigDecimal.ZERO);
                studentCreditsMapper.insert(studentCredits);
            }
        }
        return Result.success(student);
    }
    
    @Override
    public Result getStudentById(HttpServletRequest httpServletRequest, Long id) {
        Student selectById = studentMapper.selectById(id);
        selectById.setPhone(StringUtils.phoneDesensitization(selectById.getPhone()));
        selectById.setName(StringUtils.nameDesensitization(selectById.getName()));
        selectById.setIdCard(StringUtils.custNoDesensitization(selectById.getIdCard()));
        StudentResp studentResp = new StudentResp(selectById);
        studentResp.setClassName(classMapper.selectById(selectById.getClassId()).getClassName());
        return Result.success(studentResp);
    }

    @Override
    public Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentOnConditionReq studentOnConditionReq) {
        List<Student> studentList = studentMapper.selectByCondition(studentOnConditionReq);
        List<StudentResp> studentRespList = new ArrayList<>();
        for (Student student : studentList) {
            StudentResp studentResp = new StudentResp(student);
            studentResp.setPhone(StringUtils.phoneDesensitization(studentResp.getPhone()));
            studentResp.setName(StringUtils.nameDesensitization(studentResp.getName()));
            studentResp.setIdCard(StringUtils.custNoDesensitization(studentResp.getIdCard()));
            studentResp.setClassName(classMapper.selectById(student.getClassId()).getClassName());
            studentRespList.add(studentResp);
        }
        IPage<StudentResp> page = PageUtil.getPage(new Page<>(), studentOnConditionReq.getPageNo(), studentOnConditionReq.getPageSize(), studentRespList);
        return Result.success(page);
    }

    @Override
    public Result login(HttpServletRequest httpServletRequest, StudentLoginReq studentLoginReq) {
        Student selectById = studentMapper.selectById(studentLoginReq.getId());
        if (selectById == null || selectById.getStatus() == 1) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        String salt = selectById.getSalt();
        String md5Password = DigestUtil.md5Hex(studentLoginReq.getPassword() + salt);
        if (!md5Password.equals(selectById.getPassword())) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        Long token = SnowflakeUtil.nextId();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", selectById.getId().toString());
        hashMap.put("name", selectById.getName());
        hashMap.put("classId", selectById.getClassId().toString());
        redisTemplate.opsForValue().set(RedisConstant.STUDENT_TOKEN_PREFIX + token.toString(), JSON.toJSONString(hashMap), 86400, TimeUnit.SECONDS);
        redisTemplate.delete(RedisConstant.CHECK_CODE_PREFIX + studentLoginReq.getId());
        hashMap.put("token", token.toString());
        return Result.success(hashMap);
    }

    @Override
    public Result getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Long studentId) throws IOException {
        StringBuilder randomString = new StringBuilder();
        for (int i=1; i<=6; i++) {
            String rand = ValidateCodeUtil.getRandomString(new Random().nextInt(62));
            randomString.append(rand);
        }
        redisTemplate.opsForValue().set((RedisConstant.CHECK_CODE_PREFIX + studentId), randomString.toString(), 120, TimeUnit.SECONDS);
        BufferedImage image = ValidateCodeUtil.getRandomCodeImage(randomString.toString());
        ImageIO.write(image, "PNG", httpServletResponse.getOutputStream());
        return null;
    }

    @Override
    public Result logout(HttpServletRequest httpServletRequest, Long token) {
        redisTemplate.delete(RedisConstant.STUDENT_TOKEN_PREFIX + token.toString());
        return Result.success();
    }

    @Override
    public Result batchAddStudent(HttpServletRequest httpServletRequest, MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), StudentExcel.class, new StudentExcelListener(studentMapper)).sheet().doRead();
        return Result.success();
    }

}
