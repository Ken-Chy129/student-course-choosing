package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.CourseUtil;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.req.ClassCourseListReq;
import cn.ken.student.rubcourse.dto.resp.ClassCourseListResp;
import cn.ken.student.rubcourse.entity.ClassCourse;
import cn.ken.student.rubcourse.entity.StudentCourse;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.service.IClassCourseService;
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
    public Result getClassCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {

        // 获取学生已选课程表
        List<StudentCourse> studentCourses = studentCourseMapper.getStudentCourse(classCourseListReq.getStudentId(), classCourseListReq.getSemester());
        
        // 获取方案内课程的开课班信息
        List<ClassCourseListResp> courseClassInfoRespList = courseClassMapper.getCourseClassInfoList(classCourseListReq);

        for (ClassCourseListResp classCourseListResp : courseClassInfoRespList) {
            courseUtil.setPlaceTimeAndIsConflict(classCourseListResp.getCourseClassInfoResps(), studentCourses);
        }

        IPage<ClassCourseListResp> page = PageUtil.getPage(new Page<>(), classCourseListReq.getPageNo(), classCourseListReq.getPageSize(), courseClassInfoRespList);
        
        return Result.success(page);
    }

}
