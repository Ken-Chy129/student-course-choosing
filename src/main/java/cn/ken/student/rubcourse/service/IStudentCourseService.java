package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.StudentCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学生选课表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IStudentCourseService extends IService<StudentCourse> {

    Result getStudentChooseLog(HttpServletRequest httpServletRequest, Long studentId, Integer semester, Boolean isChosen);

    Result chooseCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse);

}
