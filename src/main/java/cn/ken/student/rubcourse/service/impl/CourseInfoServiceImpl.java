package cn.ken.student.rubcourse.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.entity.CourseInfo;
import cn.ken.student.rubcourse.entity.CourseTimeplace;
import cn.ken.student.rubcourse.mapper.CourseInfoMapper;
import cn.ken.student.rubcourse.mapper.CourseTimeplaceMapper;
import cn.ken.student.rubcourse.service.ICourseInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    private static Integer num = 10000;
    
    @Autowired
    private CourseInfoMapper courseInfoMapper;
    
    @Autowired
    private CourseTimeplaceMapper courseTimeplaceMapper;

    @Override
    public Result getCourseInfoList(HttpServletRequest httpServletRequest, CourseInfoListReq courseInfoListReq) {
        List<CourseInfo> courseInfoList = courseInfoMapper.getCourseInfoList(courseInfoListReq);
        
        return Result.success(courseInfoList);
    }

    @Override
    @Transactional
    public Result addCourseInfo(HttpServletRequest httpServletRequest, CourseInfoAddReq courseInfoAddReq) {
        CourseInfo courseInfo = getCourseInfo(courseInfoAddReq);
        for (int i=0; i < courseInfoAddReq.getDurationList().size(); i++) {
            CourseTimeplace courseTimeplace = new CourseTimeplace();
            courseTimeplace.setCourseId(courseInfo.getId());
            courseTimeplace.setDurationTime(courseInfoAddReq.getDurationList().get(i));
            courseTimeplace.setPlace(courseInfoAddReq.getPlaceList().get(i));
            courseTimeplace.setWeekDay(courseInfoAddReq.getWeekDayList().get(i));
            courseTimeplace.setDayNo(courseInfoAddReq.getDayNoList().get(i));
            courseTimeplaceMapper.insert(courseTimeplace);
        }
        courseInfoMapper.insert(courseInfo);
        return Result.success();
    }

    private CourseInfo getCourseInfo(CourseInfoAddReq courseInfoAddReq) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseName(courseInfoAddReq.getCourseName());
        courseInfo.setType(courseInfoAddReq.getType());
        courseInfo.setCampus(courseInfoAddReq.getCampus());
        courseInfo.setCapacity(courseInfoAddReq.getCapacity());
        courseInfo.setCredit(courseInfoAddReq.getCredit());
        courseInfo.setCollegeId(courseInfoAddReq.getCollegeId());
        courseInfo.setGeneralType(courseInfoAddReq.getGeneralType());
        courseInfo.setIsMooc(courseInfoAddReq.getIsMooc());
        courseInfo.setExamTime(courseInfoAddReq.getExamTime());
        courseInfo.setExamType(courseInfoAddReq.getExamType());
        courseInfo.setLanguage(courseInfoAddReq.getLanguage());
        courseInfo.setTeacher(courseInfoAddReq.getTeacher());
        String id = courseInfo.getCampus().toString() + (courseInfo.getCollegeId().toString().length() == 1 ? "0" : "") + courseInfo.getCollegeId().toString() + courseInfo.getType().toString() + (courseInfo.getGeneralType().toString().length() == 1 ? "0" : "") + (num++).toString();
        courseInfo.setId(id);
        return courseInfo;
    }
}
