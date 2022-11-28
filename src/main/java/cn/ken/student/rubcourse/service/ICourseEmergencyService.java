package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.CourseEmergency;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程应急设置 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ICourseEmergencyService extends IService<CourseEmergency> {

    Result addCourseEmergency(HttpServletRequest httpServletRequest, CourseEmergency courseEmergency);
}
