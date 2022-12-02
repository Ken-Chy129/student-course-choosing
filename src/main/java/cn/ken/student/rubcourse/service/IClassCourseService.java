package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.ClassCourseListReq;
import cn.ken.student.rubcourse.entity.ClassCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 方案内课程 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IClassCourseService extends IService<ClassCourse> {

    Result addClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse);
    
    Result updateClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse);

    Result removeClassCourse(HttpServletRequest httpServletRequest, Integer id);

    Result getRecommendedCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq);

    Result getClassCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq);
}
