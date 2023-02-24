package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.model.entity.CourseEmergency;
import cn.ken.student.rubcourse.mapper.CourseEmergencyMapper;
import cn.ken.student.rubcourse.service.ICourseEmergencyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程应急设置 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class CourseEmergencyServiceImpl extends ServiceImpl<CourseEmergencyMapper, CourseEmergency> implements ICourseEmergencyService {

    @Autowired
    private CourseEmergencyMapper courseEmergencyMapper;
    
    @Override
    public Result addCourseEmergency(HttpServletRequest httpServletRequest, CourseEmergency courseEmergency) {
        courseEmergency.setId(SnowflakeUtil.nextId());
        courseEmergencyMapper.insert(courseEmergency);
        return Result.success();
    }

    @Override
    public Result deleteCourseEmergency(HttpServletRequest httpServletRequest, Long id) {
        CourseEmergency courseEmergency = courseEmergencyMapper.selectById(id);
        courseEmergency.setIsDeleted(true);
        courseEmergencyMapper.updateById(courseEmergency);
        return Result.success();
    }
}
