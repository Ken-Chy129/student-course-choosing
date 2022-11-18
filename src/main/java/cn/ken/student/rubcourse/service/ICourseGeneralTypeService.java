package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.CourseGeneralType;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 通识课类别表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ICourseGeneralTypeService extends IService<CourseGeneralType> {

    Result getCourseGeneralTypeList(HttpServletRequest httpServletRequest);

    Result addCourseGeneralType(HttpServletRequest httpServletRequest, String generalTypeName);
}
