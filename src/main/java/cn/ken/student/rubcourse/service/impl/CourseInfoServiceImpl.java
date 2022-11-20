package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.*;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.CourseDetailResp;
import cn.ken.student.rubcourse.dto.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.dto.CourseNameListResp;
import cn.ken.student.rubcourse.entity.*;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.service.ICourseInfoService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程信息表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo> implements ICourseInfoService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private CourseInfoMapper courseInfoMapper;
    
    @Autowired
    private CourseTimeplaceMapper courseTimeplaceMapper;
    
    @Autowired
    private CollegeMapper collegeMapper;
    
    @Autowired
    private CourseDependenceMapper courseDependenceMapper;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public Result getCourseInfoList(HttpServletRequest httpServletRequest, String searchContent) {
        List<CourseNameListResp> courseNameList = courseInfoMapper.getCourseNameList(searchContent);
        return Result.success(courseNameList);
    }

    @Override
    public Result getCourseInfoPage(HttpServletRequest httpServletRequest, CourseInfoListReq courseInfoListReq) {
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
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getStudentId, id)
                .eq(StudentCourse::getSemester, chooseRound.getSemester())
                .eq(StudentCourse::getIsDeleted, false);
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(queryWrapper);
        
        // 获取课程表
        List<CourseInfo> courseInfoList = courseInfoMapper.getCourseInfoPage(courseInfoListReq);
        List<CourseDetailResp> result = new ArrayList<>();
        
        for (CourseInfo courseInfo : courseInfoList) {
            // 设置上课时间地点
            CourseDetailResp courseDetailResp = new CourseDetailResp(courseInfo);
            StringBuilder placeTime = new StringBuilder();
            List<CourseTimeplace> courseTimeplaceList = courseInfo.getCourseTimeplaceList();
            StringBuilder newTime = new StringBuilder();
            for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
                newTime.append(courseTimeplace.getWeekDay()).append(courseTimeplace.getDayNo());
                placeTime.append(courseTimeplace.getDurationTime()).append(" 星期").append(WeekDayConstant.INSTANCE.get(courseTimeplace.getWeekDay()-1)).append(" ").append(courseTimeplace.getDayNo()).append(" ").append(courseTimeplace.getPlace()).append("\n");
            }
            courseDetailResp.setPlaceTime(placeTime.toString());
            
            boolean isConflict = false;
            // 判断是否冲突,遍历学生已选课程
            for (StudentCourse studentCourse : studentCourses) {
                // 获取已选课程的上课时间
                LambdaQueryWrapper<CourseTimeplace> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(CourseTimeplace::getCourseId, studentCourse.getCourseId())
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
            courseDetailResp.setIsConflict(isConflict);
            result.add(courseDetailResp);
        }
        IPage<CourseDetailResp> page = PageUtil.getPage(new Page<>(), courseInfoListReq.getPageNo(), courseInfoListReq.getPageSize(), result);
        return Result.success(page);
    }

    @Override
    @Transactional
    public Result addCourseInfo(HttpServletRequest httpServletRequest, CourseInfoAddReq courseInfoAddReq) {
        CourseInfo courseInfo = getCourseInfo(courseInfoAddReq);
        for (int i=0; i<courseInfoAddReq.getDurationList().size(); i++) {
            CourseTimeplace courseTimeplace = new CourseTimeplace();
            courseTimeplace.setId(SnowflakeUtil.nextId());
            courseTimeplace.setCourseId(courseInfo.getId());
            courseTimeplace.setDurationTime(courseInfoAddReq.getDurationList().get(i));
            courseTimeplace.setPlace(courseInfoAddReq.getPlaceList().get(i));
            courseTimeplace.setWeekDay(courseInfoAddReq.getWeekDayList().get(i));
            courseTimeplace.setDayNo(DayNoConstant.INSTANCE.get(courseInfoAddReq.getDayNoList().get(i)));
            courseTimeplaceMapper.insert(courseTimeplace);
        }
        for (String preCourseId : courseInfoAddReq.getPreCourseIdList()) {
            CourseDependence courseDependence = new CourseDependence();
            courseDependence.setId(SnowflakeUtil.nextId());
            courseDependence.setCourseId(courseInfo.getId());
            courseDependence.setPreCourseId(preCourseId);
            courseDependenceMapper.insert(courseDependence);
        }
        courseInfoMapper.insert(courseInfo);
        return Result.success();
    }

    @Override
    public Result removeCourseInfo(HttpServletRequest httpServletRequest, List<String> courseInfoIds) {
        LambdaUpdateWrapper<CourseInfo> updateWrapper0 = new LambdaUpdateWrapper<>();
        updateWrapper0.in(CourseInfo::getId, courseInfoIds)
                .set(CourseInfo::getStatus, 1);
        courseInfoMapper.update(null, updateWrapper0);
        LambdaUpdateWrapper<CourseTimeplace> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(CourseTimeplace::getCourseId, courseInfoIds)
                .set(CourseTimeplace::getIsDeleted, true);
        courseTimeplaceMapper.update(null, updateWrapper);
        LambdaUpdateWrapper<CourseDependence> updateWrapper1 = new LambdaUpdateWrapper<>();
        updateWrapper1.in(CourseDependence::getCourseId, courseInfoIds)
                .set(CourseDependence::getIsDeleted, true);
        courseDependenceMapper.update(null, updateWrapper1);
        // todo:更新学生学分表以及删除学生选课表记录并发出通告
        return Result.success();
    }

    private CourseInfo getCourseInfo(CourseInfoAddReq courseInfoAddReq) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseName(courseInfoAddReq.getCourseName());
        courseInfo.setType(CourseTypeConstant.INSTANCE.get(courseInfoAddReq.getType()));
        courseInfo.setCampus(CampusConstant.INSTANCE.get(courseInfoAddReq.getCampus()));
        courseInfo.setCapacity(courseInfoAddReq.getCapacity());
        courseInfo.setCredit(courseInfoAddReq.getCredit());
        courseInfo.setCollege(collegeMapper.selectById(courseInfoAddReq.getCollege()).getCollegeName());
        if (courseInfoAddReq.getGeneralType() != null) {
            courseInfo.setGeneralType(GeneralTypeConstant.INSTANCE.get(courseInfoAddReq.getGeneralType()));
        }
        courseInfo.setIsMooc(courseInfoAddReq.getIsMooc());
        courseInfo.setExamTime(courseInfoAddReq.getExamTime());
        courseInfo.setExamType(ExamTypeConstant.INSTANCE.get(courseInfoAddReq.getExamType()));
        courseInfo.setLanguage(LanguageTypeConstant.INSTANCE.get(courseInfoAddReq.getLanguage()));
        courseInfo.setTeacher(courseInfoAddReq.getTeacher());
        String courseNum = redisTemplate.opsForValue().get("course_num");
        assert courseNum != null;
        redisTemplate.opsForValue().set("courseNum", String.valueOf(Integer.parseInt(courseNum)+1));
        String id = courseInfoAddReq.getCampus() + (courseInfoAddReq.getCollege().toString().length() == 1 ? "0" : "") + courseInfoAddReq.getCollege().toString() + courseInfoAddReq.getType().toString() + (courseInfoAddReq.getGeneralType() == null ? "0" : String.valueOf(courseInfoAddReq.getGeneralType()+1)) + courseNum;
        courseInfo.setId(id);
        return courseInfo;
    }
}
