package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.sys.req.DepartmentAddReq;
import cn.ken.student.rubcourse.service.sys.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/department")
@Api(tags = "系管理")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;
    
    @PostMapping("list")
    @ApiOperation("查看所有系")
    public Result getDepartmentList(HttpServletRequest httpServletRequest, Integer collegeId) {
        return departmentService.getDepartmentList(httpServletRequest, collegeId);
    }

    @PostMapping("addDepartment")
    @ApiOperation("增加系")
    public Result addDepartment(HttpServletRequest httpServletRequest, DepartmentAddReq departmentAddReq) {
        return departmentService.addDepartment(httpServletRequest, departmentAddReq);
    }
}
