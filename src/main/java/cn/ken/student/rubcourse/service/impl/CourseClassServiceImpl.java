package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.ComboBoxConstant;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.CourseUtil;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.mapper.CourseClassMapper;
import cn.ken.student.rubcourse.mapper.CourseMapper;
import cn.ken.student.rubcourse.mapper.CourseTimeplaceMapper;
import cn.ken.student.rubcourse.mapper.StudentCourseMapper;
import cn.ken.student.rubcourse.model.dto.req.CourseClassListReq;
import cn.ken.student.rubcourse.model.dto.resp.CourseClassInfoResp;
import cn.ken.student.rubcourse.model.entity.Course;
import cn.ken.student.rubcourse.model.entity.CourseClass;
import cn.ken.student.rubcourse.model.entity.CourseTimeplace;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
import cn.ken.student.rubcourse.service.ICourseClassService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <pre>
 * <p>课程信息表(SccCourseInfo)表服务实现类</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date ${DATE} ${TIME}
 */
@Slf4j
@Service
public class CourseClassServiceImpl extends ServiceImpl<CourseClassMapper, CourseClass> implements ICourseClassService {

    @Autowired
    private CourseClassMapper courseClassMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseTimeplaceMapper courseTimeplaceMapper;

    @Autowired
    private CourseUtil courseUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result getAllCourseInfoPage(HttpServletRequest httpServletRequest, CourseClassListReq req) {
        // 拿到满足的课程的map
        Map<String, Course> courseMap = courseUtil.getCourses(req.getCampus(), req.getCollege(), req.getGeneralType(), req.getCredit());

        // 拿到满足时间条件的课程班的list
        List<CourseTimeplace> courseTimeplaceList = courseUtil.getCourseTimePlaces(req.getWeekDay());
        // 拿到目标课程班id集合
        Set<Long> courseClassIdSet = courseTimeplaceList
                .stream()
                .map(CourseTimeplace::getCourseClassId)
                .collect(Collectors.toSet());

        String searchContent = req.getSearchContent();
        // 分页查询，根据上面拿到的满足的id列表以及学生的查询条件进行筛选
        Page<CourseClass> courseClassPage = new Page<>(req.getPageNo(), req.getPageSize());
        LambdaQueryWrapper<CourseClass> courseClassLambdaQueryWrapper = new LambdaQueryWrapper<>();
        courseClassLambdaQueryWrapper
                .in(CourseClass::getCourseId, courseMap.keySet())
                .in(CourseClass::getId, courseClassIdSet)
                .and(qw -> qw
                        .like(StringUtils.isNotBlank(searchContent), CourseClass::getCourseId, searchContent)
                        .or()
                        .like(StringUtils.isNotBlank(searchContent), CourseClass::getTeacher, searchContent)
                        .or()
                        .like(StringUtils.isNotBlank(searchContent), CourseClass::getCourseName, searchContent)
                        .eq(CourseClass::getIsDeleted, 0))
                .eq(req.getIsMooc() != null, CourseClass::getIsMooc, req.getIsMooc());
        courseClassPage = courseClassMapper.selectPage(courseClassPage, courseClassLambdaQueryWrapper);

        // 获取学生已选课程表
        List<StudentCourse> studentCourseClassList = courseUtil.getStudentCourseClasses(req.getStudentId(), req.getSemester());
        Set<Long> studentCourseClassIdSet = studentCourseClassList
                .stream()
                .map(StudentCourse::getCourseClassId)
                .collect(Collectors.toSet());

        // 查询结果集
        List<CourseClassInfoResp> courseClassInfoRespList = new ArrayList<>(req.getPageSize());
        // 遍历当前要显示的课程班，为结果集填充信息
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (CourseClass courseClass : courseClassPage.getRecords()) {
            Long courseClassId = courseClass.getId();
            // 通过判断当前课程班id是否存在在学生已选课程班id集合种来判断是否已选
            boolean isChoose = studentCourseClassIdSet.contains(courseClassId);
            // 如果已选则不需要进行后半部分判断，直接显示已选课，未选则需要判断该课程是否冲突
            boolean isConflict = isChoose || courseUtil.isConflict(courseClassId, studentCourseClassList, courseTimeplaceList);
            // 构建vo
            CourseClassInfoResp build = CourseClassInfoResp.builder()
                    .placeTime(courseUtil.getPlaceTime(courseClassId, courseTimeplaceList))
                    .isChoose(isChoose)
                    .isConflict(isConflict)
                    .build();
            build.fillWithCourseClass(courseClass);
            build.fillWithCourse(courseMap.get(courseClass.getCourseId()));
            // 添加到结果集
            courseClassInfoRespList.add(build);
        }
        stopWatch.stop();
        log.info("==========消耗时间:{}", stopWatch.getTotalTimeMillis());
        IPage<CourseClassInfoResp> page = PageUtil.getPage(new Page<>(), req.getPageNo(), req.getPageSize(), courseClassInfoRespList);
        return Result.success(page);
    }

    @Override
    public Result getCourseClassByCourseId(HttpServletRequest httpServletRequest, String courseId) {
        List<CourseClass> courseClassList = courseClassMapper.selectByCourseId(courseId);
        for (CourseClass courseClass : courseClassList) {
            List<CourseTimeplace> courseTimeplaceList = courseClass.getCourseTimeplaceList();
            StringBuilder placeTime = new StringBuilder();
            for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
                placeTime.append(courseTimeplace.getDurationTime()).append(" 星期").append(ComboBoxConstant.WeekDayConstant.INSTANCE.get(courseTimeplace.getWeekDay() - 1)).append(" ").append(courseTimeplace.getDayNo()).append(" ").append(courseTimeplace.getPlace()).append("\n");
            }
            courseClass.setCourseTimeplace(placeTime.toString());
        }
        return Result.success(courseClassList);
    }

}

