package cn.ken.student.rubcourse.common.util;

import cn.ken.student.rubcourse.common.constant.WeekDayConstant;
import cn.ken.student.rubcourse.dto.resp.CourseClassInfoResp;
import cn.ken.student.rubcourse.dto.resp.StudentChooseLogResp;
import cn.ken.student.rubcourse.entity.CourseTimeplace;
import cn.ken.student.rubcourse.entity.StudentCourse;
import cn.ken.student.rubcourse.mapper.CourseTimeplaceMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private CourseTimeplaceMapper courseTimeplaceMapper;
    
    public void setPlaceTimeAndIsConflict1(List<CourseClassInfoResp> courseClassInfoRespList, List<StudentCourse> studentCourses) {
        for (CourseClassInfoResp courseClassInfoResp : courseClassInfoRespList) {
            // 设置上课时间地点
            List<CourseTimeplace> courseTimeplaceList = courseClassInfoResp.getCourseTimeplaceList();
            List<String> courseTimes = new ArrayList<>();
            StringBuilder placeTime = new StringBuilder();
            for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
                courseTimes.add(courseTimeplace.getWeekDay().toString() + courseTimeplace.getDayNo());
                placeTime.append(courseTimeplace.getDurationTime()).append(" 星期").append(WeekDayConstant.INSTANCE.get(courseTimeplace.getWeekDay()-1)).append(" ").append(courseTimeplace.getDayNo()).append(" ").append(courseTimeplace.getPlace()).append("\n");
            }
            courseClassInfoResp.setPlaceTime(placeTime.toString());

            // 如果已经选择了该门课则不需判断该课是否冲突
            if (courseClassInfoResp.getIsChoose()) {
                continue;
            }
            
            boolean isConflict = false;
            // 判断是否冲突,遍历学生已选课程
            for (StudentCourse studentCourse : studentCourses) {
                // 获取已选课程的上课时间
                LambdaQueryWrapper<CourseTimeplace> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(CourseTimeplace::getCourseClassId, studentCourse.getCourseClassId())
                        .eq(CourseTimeplace::getIsDeleted, false);
                List<CourseTimeplace> chooseCourseTimePlaces = courseTimeplaceMapper.selectList(queryWrapper1);
                // 遍历已选课程的上课时间
                for (CourseTimeplace chooseCourseTimePlace : chooseCourseTimePlaces) {
                    // 如果课程上课时间与已选课程存在相同，则冲突并结束判断
                    for (String courseTime : courseTimes) {
                        if (courseTime.equals(chooseCourseTimePlace.getWeekDay().toString()+chooseCourseTimePlace.getDayNo())) {
                            isConflict = true;
                            break;
                        }
                        if (isConflict) {
                            break;
                        }
                    }
                }
                if (isConflict) {
                    break;
                }
            }
            courseClassInfoResp.setIsConflict(isConflict);
        }
    }

    public void setPlaceTimeAndIsConflict2(List<StudentChooseLogResp> studentChooseLogRespList, List<StudentCourse> studentCourses) {
        for (StudentChooseLogResp studentChooseLogResp : studentChooseLogRespList) {
            // 设置上课时间地点
            List<CourseTimeplace> courseTimeplaceList = studentChooseLogResp.getCourseTimeplaceList();
            List<String> courseTimes = new ArrayList<>();
            StringBuilder placeTime = new StringBuilder();
            for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
                courseTimes.add(courseTimeplace.getWeekDay().toString() + courseTimeplace.getDayNo());
                placeTime.append(courseTimeplace.getDurationTime()).append(" 星期").append(WeekDayConstant.INSTANCE.get(courseTimeplace.getWeekDay()-1)).append(" ").append(courseTimeplace.getDayNo()).append(" ").append(courseTimeplace.getPlace()).append("\n");
            }
            studentChooseLogResp.setPlaceTime(placeTime.toString());

            // 如果已经选择了该门课则不需判断该课是否冲突
            if (studentChooseLogResp.getIsChoose()) {
                continue;
            }

            boolean isConflict = false;
            // 判断是否冲突,遍历学生已选课程
            for (StudentCourse studentCourse : studentCourses) {
                // 获取已选课程的上课时间
                LambdaQueryWrapper<CourseTimeplace> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(CourseTimeplace::getCourseClassId, studentCourse.getCourseClassId())
                        .eq(CourseTimeplace::getIsDeleted, false);
                List<CourseTimeplace> chooseCourseTimePlaces = courseTimeplaceMapper.selectList(queryWrapper1);
                // 遍历已选课程的上课时间
                for (CourseTimeplace chooseCourseTimePlace : chooseCourseTimePlaces) {
                    // 如果课程上课时间与已选课程存在相同，则冲突并结束判断
                    for (String courseTime : courseTimes) {
                        if (courseTime.equals(chooseCourseTimePlace.getWeekDay().toString()+chooseCourseTimePlace.getDayNo())) {
                            isConflict = true;
                            break;
                        }
                        if (isConflict) {
                            break;
                        }
                    }
                }
                if (isConflict) {
                    break;
                }
            }
            studentChooseLogResp.setIsConflict(isConflict);
        }
    }
    
}
