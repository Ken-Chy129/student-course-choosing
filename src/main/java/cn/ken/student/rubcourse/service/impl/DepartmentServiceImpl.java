package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.sys.req.DepartmentAddReq;
import cn.ken.student.rubcourse.model.entity.Department;
import cn.ken.student.rubcourse.mapper.DepartmentMapper;
import cn.ken.student.rubcourse.service.IDepartmentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 系表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Override
    public Result getDepartmentList(HttpServletRequest httpServletRequest, Integer collegeId) {
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Department::getCollegeId, collegeId);
        List<Department> departments = departmentMapper.selectList(queryWrapper);
        return Result.success(departments);
    }

    @Override
    public Result addDepartment(HttpServletRequest httpServletRequest, DepartmentAddReq departmentAddReq) {
        Department department = new Department();
        department.setCollegeId(departmentAddReq.getCollegeId());
        department.setDepartmentName(departmentAddReq.getDepartmentName());
        departmentMapper.insert(department);
        return Result.success();
    }
}
