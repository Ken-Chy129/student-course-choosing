package cn.ken.student.rubcourse.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.IpUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.StudentReq;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import red.zyc.desensitization.Sensitive;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @Override
    public Result addStudent(HttpServletRequest httpServletRequest, Student student) throws Exception {
        Student selectById = studentMapper.selectById(student.getId());
        if (selectById != null) {
            throw new BusinessException(ErrorCodeEnums.STUDENT_EXISTS);
        }
        // todo:查询班级是否存在
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
