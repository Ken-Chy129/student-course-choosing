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
import cn.ken.student.rubcourse.dto.req.StudentLoginReq;
import cn.ken.student.rubcourse.dto.req.StudentOnClassReq;
import cn.ken.student.rubcourse.dto.req.StudentOnConditionReq;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.mapper.ClassMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IStudentService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result addStudent(HttpServletRequest httpServletRequest, Student student) throws Exception {
        Student selectById = studentMapper.selectById(student.getId());
        if (selectById != null) {
            throw new BusinessException(ErrorCodeEnums.STUDENT_EXISTS);
        }
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getId, student.getClassId());
        Class result = classMapper.selectOne(queryWrapper);
        if (result != null) {
            throw new BusinessException(ErrorCodeEnums.CLASS_NOT_EXISTS);
        }
        String salt = IdUtil.simpleUUID();
        student.setSalt(salt);
        String md5Password = DigestUtil.md5Hex(student.getPassword() + salt);
        student.setPassword(md5Password);
        studentMapper.insert(student);
        return Result.success(student);
    }
    
    @Override
    public Result getStudentById(HttpServletRequest httpServletRequest, Long id) {
        Student selectById = studentMapper.selectById(id);
        selectById.setPhone(StringUtils.phoneDesensitization(selectById.getPhone()));
        selectById.setName(StringUtils.nameDesensitization(selectById.getName()));
        selectById.setIdCard(StringUtils.custNoDesensitization(selectById.getIdCard()));
        return Result.success(selectById);
    }

    @Override
    public Result getStudentByClassId(HttpServletRequest httpServletRequest, StudentOnClassReq studentOnClassReq) {
        List<Student> studentList = studentMapper.selectByClassId(studentOnClassReq.getClassId());
        IPage<Student> page = PageUtil.getPage(new Page<>(), studentOnClassReq.getPageNo(), studentOnClassReq.getPageSize(), studentList);
        return Result.success(page);
    }

    @Override
    public Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentOnConditionReq studentOnConditionReq) {
        List<Student> studentList = studentMapper.selectByCondition(studentOnConditionReq);
        IPage<Student> page = PageUtil.getPage(new Page<>(), studentOnConditionReq.getPageNo(), studentOnConditionReq.getPageSize(), studentList);
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
    
}
