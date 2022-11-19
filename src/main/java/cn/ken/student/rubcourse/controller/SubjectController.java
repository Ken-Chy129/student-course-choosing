package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.service.IDepartmentService;
import cn.ken.student.rubcourse.service.ISubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    @PostMapping("list")
    @ApiOperation("查看某个系所有专业")
    public Result getSubjectList(HttpServletRequest httpServletRequest, Integer departmentId) {
        return subjectService.getSubjectList(httpServletRequest, departmentId);
    }

    @PostMapping("addDepartment")
    @ApiOperation("增加专业")
    public Result addDepartment(HttpServletRequest httpServletRequest, Integer departmentId, String subjectName) {
        return subjectService.addSubject(httpServletRequest, departmentId, subjectName);
    }
}
