package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.constant.WeekDayConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.dto.resp.AllCourseListResp;
import cn.ken.student.rubcourse.dto.req.CourseClassListReq;
import cn.ken.student.rubcourse.entity.*;
import cn.ken.student.rubcourse.mapper.CourseMapper;
import cn.ken.student.rubcourse.mapper.CourseTimeplaceMapper;
import cn.ken.student.rubcourse.mapper.StudentCourseMapper;
import cn.ken.student.rubcourse.service.ICourseClassService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ken.student.rubcourse.mapper.CourseClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 * <p>课程信息表(SccCourseInfo)表服务实现类</p>
 * </pre>
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date  ${DATE} ${TIME}
 */
@Service
public class CourseClassServiceImpl extends ServiceImpl<CourseClassMapper, CourseClass> implements ICourseClassService {

    @Autowired
    private CourseClassMapper courseClassMapper;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Autowired
    private CourseTimeplaceMapper courseTimeplaceMapper;
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public Result getAllCourseInfoPage(HttpServletRequest httpServletRequest, CourseClassListReq courseClassListReq) {
        // 获取学生信息，todo:后续取消注释，当前直接写死
//        String token = httpServletRequest.getHeader("token");
//        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(token), HashMap.class);
//        if (hashMap == null) {
//            return Result.fail(ErrorCodeEnums.LOGIN_CREDENTIAL_EXPIRED);
//        }
//        Long id = Long.valueOf(hashMap.get("id"));
        
        Long id = 2020101602L;
        // 获取轮次信息
        ChooseRound chooseRound = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.PRESENT_ROUND), ChooseRound.class);
        if (chooseRound == null) {
            return Result.fail(ErrorCodeEnums.NO_ROUND_PRESENT);
        }
        
        // 获取学生已选课程表
        List<StudentCourse> studentCourses = studentCourseMapper.getStudentCourse(id, chooseRound.getSemester());

        // 获取课程表
        List<AllCourseListResp> courseInfoPage = courseClassMapper.getCourseInfoPage(courseClassListReq);
        for (AllCourseListResp allCourseListResp : courseInfoPage) {
            // 设置上课时间地点
            StringBuilder placeTime = new StringBuilder();
            List<CourseTimeplace> courseTimeplaceList = allCourseListResp.getCourseTimeplaceList();
            StringBuilder newTime = new StringBuilder();
            for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
                newTime.append(courseTimeplace.getWeekDay()).append(courseTimeplace.getDayNo());
                placeTime.append(courseTimeplace.getDurationTime()).append(" 星期").append(WeekDayConstant.INSTANCE.get(courseTimeplace.getWeekDay()-1)).append(" ").append(courseTimeplace.getDayNo()).append(" ").append(courseTimeplace.getPlace()).append("\n");
            }
            allCourseListResp.setPlaceTime(placeTime.toString());

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
                    if (newTime.toString().equals(chooseCourseTimePlace.getWeekDay().toString()+chooseCourseTimePlace.getDayNo())) {
                        isConflict = true;
                        break;
                    }
                }
                if (isConflict) {
                    break;
                }
            }
            allCourseListResp.setIsConflict(isConflict);
        }
        IPage<AllCourseListResp> page = PageUtil.getPage(new Page<>(), courseClassListReq.getPageNo(), courseClassListReq.getPageSize(), courseInfoPage);
        
        return Result.success(page);
    }

}

