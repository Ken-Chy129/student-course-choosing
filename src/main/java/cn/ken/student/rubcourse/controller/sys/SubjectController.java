package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.sys.req.SubjectAddReq;
import cn.ken.student.rubcourse.service.sys.ISubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 专业表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/subject")
@Api(tags = "专业管理")
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    @PostMapping("list")
    @ApiOperation("查看某个系所有专业")
    public Result getSubjectList(HttpServletRequest httpServletRequest, Integer departmentId) {
        return subjectService.getSubjectList(httpServletRequest, departmentId);
    }

    @PostMapping("add")
    @ApiOperation("增加专业")
    public Result addSubject(HttpServletRequest httpServletRequest, @RequestBody SubjectAddReq subjectAddReq) {
        return subjectService.addSubject(httpServletRequest, subjectAddReq);
    }
}
