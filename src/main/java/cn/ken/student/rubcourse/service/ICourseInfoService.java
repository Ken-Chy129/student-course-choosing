package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.entity.CourseInfo;
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
public interface ICourseInfoService extends IService<CourseInfo> {

    Result getCourseInfoList(HttpServletRequest httpServletRequest, CourseInfoListReq courseInfoListReq);

    Result addCourseInfo(HttpServletRequest httpServletRequest, CourseInfoAddReq courseInfoAddReq);
}
