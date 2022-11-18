package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.CourseGeneralType;
import cn.ken.student.rubcourse.mapper.CourseGeneralTypeMapper;
import cn.ken.student.rubcourse.service.ICourseGeneralTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 通识课类别表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class CourseGeneralTypeServiceImpl extends ServiceImpl<CourseGeneralTypeMapper, CourseGeneralType> implements ICourseGeneralTypeService {

    @Autowired
    private CourseGeneralTypeMapper courseGeneralTypeMapper;
    
    @Override
    public Result getCourseGeneralTypeList(HttpServletRequest httpServletRequest) {
        List<CourseGeneralType> courseGeneralTypeList = courseGeneralTypeMapper.selectList(new QueryWrapper<>());
        return Result.success(courseGeneralTypeList);
    }

    @Override
    public Result addCourseGeneralType(HttpServletRequest httpServletRequest, String generalTypeName) {
        CourseGeneralType courseGeneralType = new CourseGeneralType();
        courseGeneralType.setTypeName(generalTypeName);
        courseGeneralTypeMapper.insert(courseGeneralType);
        return Result.success();
    }
}
