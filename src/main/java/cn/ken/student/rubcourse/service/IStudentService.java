package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.dto.StudentLogin;
import cn.ken.student.rubcourse.dto.StudentReq;
import cn.ken.student.rubcourse.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IStudentService extends IService<Student> {

    Result addStudent(HttpServletRequest httpServletRequest, Student student) throws Exception;

    Result getStudentById(HttpServletRequest httpServletRequest, Long id) throws Exception;

    Result getStudentByClassId(HttpServletRequest httpServletRequest, Long classId, Integer pageNo, Integer pageSize) throws Exception;

    Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentReq studentReq) throws Exception;
    
    Result login(HttpServletRequest httpServletRequest, StudentLogin studentLogin);

    Result getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Long studentId) throws IOException;

}
