package cn.ken.student.rubcourse.service.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ISysDepartmentService extends IService<Department> {

    Result getDepartmentList(HttpServletRequest httpServletRequest, Integer collegeId);
    
    Result addDepartment(HttpServletRequest httpServletRequest, Integer collegeId, String departmentName);
    
}
