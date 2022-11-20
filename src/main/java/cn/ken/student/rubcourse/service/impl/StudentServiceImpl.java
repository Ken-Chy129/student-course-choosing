package cn.ken.student.rubcourse.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.dto.StudentReq;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.mapper.ClassMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IStudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
}
