package cn.ken.student.rubcourse.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.common.util.ValidateCodeUtil;
import cn.ken.student.rubcourse.dto.StudentLogin;
import cn.ken.student.rubcourse.dto.StudentReq;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.mapper.ClassMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IStudentService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        return Result.success(studentMapper.selectById(id));
    }

    @Override
    public Result getStudentByClassId(HttpServletRequest httpServletRequest, Long classId, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentReq studentReq) {
        return null;
    }

    @Override
    public Result login(HttpServletRequest httpServletRequest, StudentLogin studentLogin) {
        String code = redisTemplate.opsForValue().get("student_code:" + studentLogin.getId());
        if (code == null) {
            return Result.fail(ErrorCodeEnums.CODE_EXPIRED);
        } else if(!code.equals(studentLogin.getCode())) {
            return Result.fail(ErrorCodeEnums.CODE_ERROR);
        }
        Student selectById = studentMapper.selectById(studentLogin.getId());
        if (selectById == null || selectById.getStatus() == 1) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        String salt = selectById.getSalt();
        String md5Password = DigestUtil.md5Hex(studentLogin.getPassword() + salt);
        if (!md5Password.equals(selectById.getPassword())) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        Long token = SnowflakeUtil.nextId();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", selectById.getId().toString());
        hashMap.put("name", selectById.getName());
        hashMap.put("classId", selectById.getClassId().toString());
        redisTemplate.opsForValue().set(token.toString(), JSON.toJSONString(hashMap));
        redisTemplate.delete("student_code:" + studentLogin.getId());
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
        redisTemplate.opsForValue().set(("student_code:" + studentId), randomString.toString(), 120, TimeUnit.SECONDS);
        BufferedImage image = ValidateCodeUtil.getRandomCodeImage(randomString.toString());
        ImageIO.write(image, "PNG", httpServletResponse.getOutputStream());
        return null;
    }
}
