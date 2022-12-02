package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.req.CourseDependencyAddReq;
import cn.ken.student.rubcourse.entity.CourseDependence;
import cn.ken.student.rubcourse.mapper.CourseDependenceMapper;
import cn.ken.student.rubcourse.service.ICourseDependenceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程依赖表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class CourseDependenceServiceImpl extends ServiceImpl<CourseDependenceMapper, CourseDependence> implements ICourseDependenceService {
    
    @Autowired
    private CourseDependenceMapper courseDependenceMapper;

    @Override
    public Result getCourseDependence(HttpServletRequest httpServletRequest, String courseId) {
        return Result.success(getCourseDependence(courseId));
    }

    @Override
    @Transactional
    public Result addCourseDependence(HttpServletRequest httpServletRequest, CourseDependencyAddReq courseDependencyAddReq) {
        String courseId = courseDependencyAddReq.getCourseId();
        for (String preCourseId : courseDependencyAddReq.getPreCourseIdList()) {
            CourseDependence courseDependence = new CourseDependence();
            if (courseId.equals(preCourseId)) {
                return Result.fail(ErrorCodeEnums.COURSE_DEPENDENCE_INVALID);
            }
            courseDependence.setId(SnowflakeUtil.nextId());
            courseDependence.setCourseId(courseId);
            courseDependence.setPreCourseId(preCourseId);
            courseDependenceMapper.insert(courseDependence);
        }
        return Result.success();
    }

    @Override
    public Result removeCourseDependence(HttpServletRequest httpServletRequest, Long id) {
        LambdaUpdateWrapper<CourseDependence> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CourseDependence::getId, id)
                .set(CourseDependence::getIsDeleted, true);
        return courseDependenceMapper.update(null, updateWrapper) > 0 ? Result.success("删除成功") : Result.fail("删除失败"); 
    }

    private List<CourseDependence> getCourseDependence(String courseId) {
        LambdaQueryWrapper<CourseDependence> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseDependence::getCourseId, courseId)
                .eq(CourseDependence::getIsDeleted, false);
        return courseDependenceMapper.selectList(queryWrapper);
    }
}
