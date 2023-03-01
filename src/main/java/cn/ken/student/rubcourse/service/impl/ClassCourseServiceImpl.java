package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.CourseUtil;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.model.dto.req.ClassCourseListReq;
import cn.ken.student.rubcourse.model.dto.resp.ClassCourseListResp;
import cn.ken.student.rubcourse.model.dto.resp.CourseClassInfoResp;
import cn.ken.student.rubcourse.model.entity.*;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.service.IClassCourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    
    @Autowired
    private CourseClassMapper courseClassMapper;
    
    @Autowired
    private CourseUtil courseUtil;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Override
    public Result addClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse) {
        classCourse.setId(SnowflakeUtil.nextId());
        classCourseMapper.insert(classCourse);
        return Result.success();
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
    public Result removeClassCourse(HttpServletRequest httpServletRequest, Long id) {
        LambdaUpdateWrapper<ClassCourse> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ClassCourse::getId, id)
                .set(ClassCourse::getIsDeleted, true);
        return classCourseMapper.update(null, updateWrapper) > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @Override
    public Result getRecommendedCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {
        classCourseListReq.setRecommendedTime(classCourseListReq.getSemester());
        return getClassCoursePage(httpServletRequest, classCourseListReq);
    }

    @Override
    public Result getClassCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq req) {
        
        // 查询课程表
        Map<String, Course> courseMap = courseUtil.getCourses();
        Set<String> courseIdSet = courseMap.values().stream().filter(course -> req.getType() == null || course.getType().equals(req.getType())).map(Course::getId).collect(Collectors.toSet());

        // 获取学生已选课程表
        Set<Long> studentCourseClassIdSet = courseUtil.getStudentCourseClasses(req.getStudentId(), req.getSemester());
        List<StudentCourse> studentCourseClassList = studentCourseMapper.selectList(new LambdaQueryWrapper<StudentCourse>().in(StudentCourse::getCourseClassId, studentCourseClassIdSet));

        // 获取上课时间地点列表
        List<CourseTimeplace> courseTimeplaceList = courseUtil.getCourseTimePlaces(null);

        // 查询满足条件课程班
        LambdaQueryWrapper<CourseClass> courseClassQueryWrapper = new LambdaQueryWrapper<>();
        courseClassQueryWrapper.eq(StringUtils.isNotBlank(req.getSearchContent()), CourseClass::getCourseId, req.getSearchContent())
                .eq(StringUtils.isNotBlank(req.getSearchContent()), CourseClass::getCourseName, req.getSearchContent())
                .in(req.getType() != null, CourseClass::getCourseId, courseIdSet)
                .eq(CourseClass::getIsDeleted, 0);
        List<CourseClass> courseClassList = courseClassMapper.selectList(courseClassQueryWrapper);

        // 获取方案内课程的开课班信息
        LambdaQueryWrapper<ClassCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassCourse::getClassId, req.getClassId())
                .eq(req.getIsMust() != null, ClassCourse::getIsMust, req.getIsMust())
                .eq(req.getRecommendedTime() != null, ClassCourse::getCommendedTime, req.getRecommendedTime())
                .in(ClassCourse::getCourseId, courseIdSet)
                .eq(ClassCourse::getIsDeleted, 0);
        List<ClassCourse> classCourseList = classCourseMapper.selectList(queryWrapper);

        List<ClassCourseListResp> res = new ArrayList<>();
        for (ClassCourse classCourse : classCourseList) {
            ClassCourseListResp classCourseListResp = new ClassCourseListResp();
            classCourseListResp.setId(classCourse.getId());
            classCourseListResp.setIsMust(classCourse.getIsMust());
            classCourseListResp.fillWithCourse(courseMap.get(classCourse.getCourseId()));
            List<CourseClassInfoResp> courseClassInfoRespList = new ArrayList<>();
            for (CourseClass courseClass : courseClassList) {
                CourseClassInfoResp courseClassInfoResp = CourseClassInfoResp.builder().build();
                if (courseClass.getCourseId().equals(classCourse.getCourseId())) {
                    courseClassInfoResp.fillWithCourseClass(courseClass);
                    courseClassInfoResp.fillWithCourse(courseMap.get(courseClass.getCourseId()));
                    courseClassInfoRespList.add(courseClassInfoResp);
                }
            }
            if (courseClassInfoRespList.size() > 0) {
                classCourseListResp.setCourseClassInfoResps(courseClassInfoRespList);
                res.add(classCourseListResp);
            }
        }

        // 设置课程是否本学期已选
        for (ClassCourseListResp classCourseListResp : res) {
            List<CourseClassInfoResp> courseClassInfoResps = classCourseListResp.getCourseClassInfoResps();
            for (CourseClassInfoResp courseClassInfoResp : courseClassInfoResps) {
                courseClassInfoResp.setIsChoose(studentCourseClassIdSet.contains(courseClassInfoResp.getId()));
            }
        }

        // 设置上课时间地点和是否冲突
        for (ClassCourseListResp classCourseListResp : res) {
            List<CourseClassInfoResp> courseClassInfoResps = classCourseListResp.getCourseClassInfoResps();
            for (CourseClassInfoResp courseClassInfoResp : courseClassInfoResps) {
                courseClassInfoResp.setIsConflict(courseUtil.isConflict(courseClassInfoResp.getId(), studentCourseClassList, courseTimeplaceList));
            }
        }

        IPage<ClassCourseListResp> page = PageUtil.getPage(new Page<>(), req.getPageNo(), req.getPageSize(), res);
        
        return Result.success(page);
    }

}
