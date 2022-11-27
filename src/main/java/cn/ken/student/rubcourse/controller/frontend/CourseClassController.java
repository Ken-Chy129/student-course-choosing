package cn.ken.student.rubcourse.controller.frontend;



import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.CourseClassListReq;
import cn.ken.student.rubcourse.service.ICourseClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <p>课程信息表(SccCourseInfo)表控制层</p>
 * </pre>
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date  ${DATE} ${TIME}
 */
@RestController
@RequestMapping("/courseClass")
@Api(tags = "课程班管理")
public class CourseClassController {
    
    @Autowired
    private ICourseClassService courseClassService;

    @GetMapping("courseClassPage")
    @ApiOperation("前台分页显示全校课程班详细信息")
    public Result getAllCourseInfoPage(HttpServletRequest httpServletRequest, CourseClassListReq courseClassListReq) {
        return courseClassService.getAllCourseInfoPage(httpServletRequest, courseClassListReq);
    }
    
}

