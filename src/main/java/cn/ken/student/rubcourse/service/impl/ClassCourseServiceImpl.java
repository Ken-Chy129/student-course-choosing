package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.ClassCourseListReq;
import cn.ken.student.rubcourse.dto.ClassCourseRecommendedListReq;
import cn.ken.student.rubcourse.entity.ClassCourse;
import cn.ken.student.rubcourse.mapper.ClassCourseMapper;
import cn.ken.student.rubcourse.service.IClassCourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 方案内课程 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class ClassCourseServiceImpl extends ServiceImpl<ClassCourseMapper, ClassCourse> implements IClassCourseService {

    @Autowired
    private ClassCourseMapper classCourseMapper;
    
    @Override
    public Result addClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse) {
        classCourse.setId(SnowflakeUtil.nextId());
        classCourseMapper.insert(classCourse);
        return Result.success();
    }

    @Override
    public Result getClassCourse(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {
        LambdaQueryWrapper<ClassCourse> queryWrapper = new LambdaQueryWrapper<>();
        Integer classId = classCourseListReq.getClassId();
        Integer recommendedTime = classCourseListReq.getRecommendedTime();
        Boolean isMust = classCourseListReq.getIsMust();
        queryWrapper.eq(classId != null, ClassCourse::getClassId, classId)
                .eq(recommendedTime != null, ClassCourse::getCommendedTime, recommendedTime)
                .eq(isMust != null, ClassCourse::getIsMust, isMust)
                .eq(ClassCourse::getIsDeleted, false);
        List<ClassCourse> classCourses = classCourseMapper.selectList(queryWrapper);
        
        IPage<ClassCourse> page = PageUtil.getPage(new Page<>(), classCourseListReq.getPageNo(), classCourseListReq.getPageSize(), classCourses);
        return Result.success(page);
    }

    @Override
    public Result updateClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse) {
        if (classCourse.getId() == null) {
            return Result.fail(ErrorCodeEnums.CLASS_COURSE_ID_NULL);
        }
        classCourseMapper.updateById(classCourse);
        return Result.success();
    }

    @Override
    public Result removeClassCourse(HttpServletRequest httpServletRequest, List<Integer> ids) {
        LambdaUpdateWrapper<ClassCourse> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(ClassCourse::getId, ids)
                .set(ClassCourse::getIsDeleted, true);
        classCourseMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result getRecommendedClassCourse(HttpServletRequest httpServletRequest, ClassCourseRecommendedListReq classCourseRecommendedListReq) {
        return null;
    }


}
