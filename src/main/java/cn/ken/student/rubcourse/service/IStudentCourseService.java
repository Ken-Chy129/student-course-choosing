package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.req.StudentChooseLogReq;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 学生选课表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IStudentCourseService extends IService<StudentCourse> {

    Result getStudentChooseLog(HttpServletRequest httpServletRequest, StudentChooseLogReq studentChooseLogReq);

    Result chooseCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse);

    Result unChooseCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse);

    void downloadStudentCourse(HttpServletRequest httpServletRequest, HttpServletResponse response, StudentChooseLogReq studentChooseLogReq) throws IOException;
}
