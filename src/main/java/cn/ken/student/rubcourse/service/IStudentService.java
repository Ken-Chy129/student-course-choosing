package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.StudentReq;
import cn.ken.student.rubcourse.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IStudentService extends IService<Student> {

    Result addStudent(HttpServletRequest httpServletRequest, Student student);

    Result getStudentById(HttpServletRequest httpServletRequest, Long id);

    Result getStudentByClassId(HttpServletRequest httpServletRequest, Long classId, Integer pageNo, Integer pageSize);

    Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentReq studentReq);
}
