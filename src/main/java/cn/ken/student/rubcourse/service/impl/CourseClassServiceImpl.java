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
        Map<String, Course> courseMap = this.getCourses(req);

        // 拿到满足时间条件的课程班的list
        List<CourseTimeplace> courseTimeplaceList = this.getCourseTimePlaces(req.getWeekDay());
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
        List<StudentCourse> studentCourseClassList = this.getStudentCourseClasses(req.getStudentId(), req.getSemester());
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
            // 获取当前课程的上课时间地点
            // 用于后续判断时间是否冲突
            List<String> courseTimes = new ArrayList<>();
            StringBuilder placeTime = new StringBuilder();
            // 要显示的课程班列表的时间地点一定封装在了courseTimeplaceList中，因为要显示的课程班列表需要满足课程班id一定在courseTimeplaceList之中
            for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
                // 从courseTimeplaceList中找到当前课程班的时间地点信息，封装到courseTimes和placeTime
                if (courseTimeplace.getCourseClassId().equals(courseClassId)) {
                    courseTimes.add(courseTimeplace.getWeekDay().toString() + courseTimeplace.getDayNo());
                    placeTime.append(courseTimeplace.getDurationTime()).append(" 星期").append(ComboBoxConstant.WeekDayConstant.INSTANCE.get(courseTimeplace.getWeekDay() - 1)).append(" ").append(courseTimeplace.getDayNo()).append(" ").append(courseTimeplace.getPlace()).append("\n");
                }
            }
            // 通过判断当前课程班id是否存在在学生已选课程班id集合种来判断是否已选
            boolean isChoose = studentCourseClassIdSet.contains(courseClassId);
            // 如果已选则不需要进行后半部分判断，直接显示已选课，未选则需要判断该课程是否冲突
            boolean isConflict = isChoose || isConflict(studentCourseClassList, courseTimes, courseTimeplaceList);
            // 构建vo
            CourseClassInfoResp build = CourseClassInfoResp.builder()
                    .placeTime(placeTime.toString())
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

    /**
     * 根据学生搜索条件，查询满足的课程的信息，首先从缓存查询，不存在则查询数据库并入缓存
     *
     * @param req 学生搜索条条件
     * @return 满足的课程的信息
     */
    private Map<String, Course> getCourses(CourseClassListReq req) {
        // 尝试从redis中拿到所有课程信息
        String value = redisTemplate.opsForValue().get(RedisConstant.COURSE_INFO);
        List<Course> list;
        if (StringUtils.isBlank(value)) {
            // 如果不存在则数据库查询并缓存入redis(此部分数据较少变动)
            list = courseMapper.selectList(new LambdaQueryWrapper<>());
            redisTemplate.opsForValue().set(RedisConstant.COURSE_INFO, JSON.toJSONString(list), 3, TimeUnit.DAYS);
        } else {
            list = JSON.parseArray(value, Course.class);
        }
        Map<String, Course> res = new HashMap<>(list.size());
        for (Course course : list) {
            // 筛选出满足学生条件的课程返回
            if (StringUtils.isBlank(req.getCampus()) || course.getCampus().equals(req.getCampus())
                    && (StringUtils.isBlank(req.getCollege()) || course.getCollege().equals(req.getCollege()))
                    && (StringUtils.isBlank(req.getGeneralType()) || course.getGeneralType().equals(req.getGeneralType()))
                    && (req.getCredit() == null || course.getCredit().equals(req.getCredit()))) {
                res.put(course.getId(), course);
            }
        }
        return res;
    }

    /**
     * 查询满足的课程的时间地点信息，首先从缓存查询，不存在则查询数据库并入缓存
     * @param weekDay 开课时间
     * @return 满足的课程时间地点信息列表
     */
    private List<CourseTimeplace> getCourseTimePlaces(Short weekDay) {
        String value = redisTemplate.opsForValue().get(RedisConstant.COURSE_CLASS_TIMEPLACE);
        List<CourseTimeplace> list;
        if (StringUtils.isBlank(value)) {
            // 如果不存在则数据库查询并缓存入redis(此部分数据较少变动)
            list = courseTimeplaceMapper.selectList(new LambdaQueryWrapper<CourseTimeplace>().eq(CourseTimeplace::getIsDeleted, 0));
            redisTemplate.opsForValue().set(RedisConstant.COURSE_CLASS_TIMEPLACE, JSON.toJSONString(list), 3, TimeUnit.DAYS);
        } else {
            list = JSON.parseArray(value, CourseTimeplace.class);
        }
        // 不需要全部返回，则需要返回满足的，因为后续需要填充该信息时需要的也肯定是满足的课程班的时间地点信息
        return list.stream().filter(courseTimeplace -> weekDay == null || weekDay.equals(courseTimeplace.getWeekDay())).toList();
    }

    /**
     * 根据学生id和学期查询当前学期学生已选课程班
     *
     * @param studentId 学生id
     * @param semester  学期
     * @return 当前学期学生已选课程班
     */
    private List<StudentCourse> getStudentCourseClasses(Long studentId, Integer semester) {
        String key = studentId + ":" + semester;
        // 尝试从redis中拿到所有学生选课信息
        String studentCourseClassList = redisTemplate.opsForValue().get(RedisConstant.COURSE_CLASS_STUDENT_CHOOSE + key);
        List<StudentCourse> list;
        if (studentCourseClassList == null) {
            list = studentCourseMapper.getStudentCourse(studentId, semester);
            redisTemplate.opsForValue().set(RedisConstant.COURSE_CLASS_STUDENT_CHOOSE + key, JSON.toJSONString(list), 3, TimeUnit.DAYS);
        } else {
            // 存在则直接转换为list
            list = JSON.parseArray(studentCourseClassList, StudentCourse.class);
        }
        return list;
    }

    /**
     * 根据学生已选课程判断目标课程是否出现上课时间冲突
     *
     * @param studentCourseClassList 学生已选课程列表
     * @param courseTimes            目标课程上课时间
     * @return 是否冲突
     */
    private boolean isConflict(List<StudentCourse> studentCourseClassList, List<String> courseTimes, List<CourseTimeplace> courseTimeplaceList) {
        boolean isConflict = false;
        // 判断是否冲突,遍历学生已选课程
        for (StudentCourse studentCourse : studentCourseClassList) {
            for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
                // 找到已选课程的上课时间
                if (courseTimeplace.getCourseClassId().equals(studentCourse.getCourseClassId())) {
                    // 如果课程上课时间与已选课程存在相同，则冲突并结束判断
                    for (String courseTime : courseTimes) {
                        if (courseTime.equals(courseTimeplace.getWeekDay().toString() + courseTimeplace.getDayNo())) {
                            isConflict = true;
                            break;
                        }
                    }
                }
                if (isConflict) {
                    break;
                }
            }
            if (isConflict) {
                break;
            }
        }
        return isConflict;
    }

    @Override
    public Result getCourseClass(HttpServletRequest httpServletRequest, String courseId) {
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

