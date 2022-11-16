package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.IpUtil;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;
    
    @Override
    public Result addStudent(Student student, HttpServletRequest httpServletRequest) {
        studentMapper.insert(student);
        System.out.println(IpUtil.getIpAddr(httpServletRequest));
        return Result.success(student);
    }
}
