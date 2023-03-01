package cn.ken.student.rubcourse.common.util;

import cn.ken.student.rubcourse.common.constant.ComboBoxConstant;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.mapper.CourseMapper;
import cn.ken.student.rubcourse.mapper.StudentCourseMapper;
import cn.ken.student.rubcourse.model.dto.req.CourseClassListReq;
import cn.ken.student.rubcourse.model.dto.resp.CourseClassInfoResp;
import cn.ken.student.rubcourse.model.dto.resp.StudentChooseLogResp;
import cn.ken.student.rubcourse.model.entity.Course;
import cn.ken.student.rubcourse.model.entity.CourseTimeplace;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
import cn.ken.student.rubcourse.mapper.CourseTimeplaceMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/27 20:51
 */
@Component
public class CourseUtil {
    
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseTimeplaceMapper courseTimeplaceMapper;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据传入的课程班id和课程班的时间地点列表，封装目标课程开课时间地点
     * @param courseClassId 课程班id
     * @param courseTimeplaceList 课程班的时间地点列表
     * @return 封装后的目标课程开课时间地点
     */
    public String getPlaceTime(Long courseClassId, List<CourseTimeplace> courseTimeplaceList) {
        StringBuilder placeTime = new StringBuilder();
        for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
            // 从courseTimeplaceList中找到当前课程班的时间地点信息，封装到courseTimes和placeTime
            if (courseTimeplace.getCourseClassId().equals(courseClassId)) {
                placeTime.append(courseTimeplace.getDurationTime()).append(" 星期").append(ComboBoxConstant.WeekDayConstant.INSTANCE.get(courseTimeplace.getWeekDay() - 1)).append(" ").append(courseTimeplace.getDayNo()).append(" ").append(courseTimeplace.getPlace()).append("\n");
            }
        }
        return placeTime.toString();
    }

    /**
     * 根据学生已选课程判断目标课程是否出现上课时间冲突
     *
     * @param courseClassId 目标课程id
     * @param studentCourseClassList 学生已选课程列表
     * @param courseTimeplaceList 课程的时间地点列表
     * @return 是否冲突
     */
    public boolean isConflict(Long courseClassId, List<StudentCourse> studentCourseClassList, List<CourseTimeplace> courseTimeplaceList) {
        List<String> courseTimes = new ArrayList<>();
        for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
            // 从courseTimeplaceList中找到当前课程班的时间地点信息，封装到courseTimes和placeTime
            if (courseTimeplace.getCourseClassId().equals(courseClassId)) {
                courseTimes.add(courseTimeplace.getWeekDay().toString() + courseTimeplace.getDayNo());
            }
        }
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

    /**
     * 获取课程信息，优先从缓存查询
     * @return 课程信息
     */
    public Map<String, Course> getCourses() {
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
            res.put(course.getId(), course);
        }
        return res;
    }

    /**
     * 根据学生搜索条件，查询满足的课程的信息，首先从缓存查询，不存在则查询数据库并入缓存
     * @return 满足的课程的信息
     */
    public Map<String, Course> getCourses(String campus,  String college, String generalType, BigDecimal credit) {
        Map<String, Course> courses = getCourses();
        Map<String, Course> res = new HashMap<>(courses.size());
        for (Course course : courses.values()) {
            // 筛选出满足学生条件的课程返回
            if (StringUtils.isBlank(campus) || course.getCampus().equals(campus)
                    && (StringUtils.isBlank(college) || course.getCollege().equals(college))
                    && (StringUtils.isBlank(generalType) || course.getGeneralType().equals(generalType))
                    && (credit == null || course.getCredit().equals(credit))) {
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
    public List<CourseTimeplace> getCourseTimePlaces(Short weekDay) {
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
    public Set<Long> getStudentCourseClasses(Long studentId, Integer semester) {
        String key = studentId + ":" + semester;
        // 尝试从redis中拿到所有学生选课信息
        Set<String> members = redisTemplate.opsForSet().members(RedisConstant.COURSE_CLASS_STUDENT_CHOOSE + key);
        Set<Long> res;
        if (members == null || members.isEmpty()) {
            List<StudentCourse> list = studentCourseMapper.getStudentCourse(studentId, semester);
            res = list.stream().map(StudentCourse::getCourseClassId).collect(Collectors.toSet());
            for (Long id : res) {
                redisTemplate.opsForSet().add(RedisConstant.COURSE_CLASS_STUDENT_CHOOSE + key, id.toString());
            }
        } else {
            res = members.stream().map(Long::valueOf).collect(Collectors.toSet());
        }
        return res;
    }
}
