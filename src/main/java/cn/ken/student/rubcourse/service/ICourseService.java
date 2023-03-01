package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.req.CourseClassAddReq;
import cn.ken.student.rubcourse.model.dto.sys.req.CourseAddReq;
import cn.ken.student.rubcourse.model.dto.sys.req.CoursePageReq;
import cn.ken.student.rubcourse.model.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程信息表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ICourseService extends IService<Course> {

    Result getCourseList(HttpServletRequest httpServletRequest, String searchContent);

    Result getCoursePage(HttpServletRequest httpServletRequest, CoursePageReq coursePageReq);
    
    Result addCourse(HttpServletRequest httpServletRequest, CourseAddReq courseAddReq);

    Result addCourseClass(HttpServletRequest httpServletRequest, CourseClassAddReq courseClassAddReq);
    
    Result removeCourse(HttpServletRequest httpServletRequest, String id);

    Result removeCourseClass(HttpServletRequest httpServletRequest, Long id);

    void preheatCourseClassInfo();
}
