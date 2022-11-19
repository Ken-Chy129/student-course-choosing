package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.CourseDependencyAddReq;
import cn.ken.student.rubcourse.entity.CourseDependence;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程依赖表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ICourseDependenceService extends IService<CourseDependence> {

    Result getCourseDependence(HttpServletRequest httpServletRequest, String courseId);
    
    Result addCourseDependence(HttpServletRequest httpServletRequest, CourseDependencyAddReq courseDependencyAddReq);
    
}
