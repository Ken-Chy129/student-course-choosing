package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.CourseClassListReq;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.ken.student.rubcourse.entity.CourseClass;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <p>课程信息表(SccCourseInfo)表服务接口</p>
 * </pre>
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date  ${DATE} ${TIME}
 */
public interface ICourseClassService extends IService<CourseClass> {

    Result getAllCourseInfoPage(HttpServletRequest httpServletRequest, CourseClassListReq courseClassListReq);
}

