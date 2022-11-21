package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.ClassCourseListReq;
import cn.ken.student.rubcourse.dto.ClassCourseRecommendedListReq;
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

    Result getClassCourse(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq);
    
    Result updateClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse);

    Result removeClassCourse(HttpServletRequest httpServletRequest, List<Integer> ids);

    Result getRecommendedClassCourse(HttpServletRequest httpServletRequest, ClassCourseRecommendedListReq classCourseRecommendedListReq);
}
