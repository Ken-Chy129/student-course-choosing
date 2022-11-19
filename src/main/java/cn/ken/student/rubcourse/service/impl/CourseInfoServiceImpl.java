package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.*;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.entity.*;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.service.ICourseInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    
    @Autowired
    private CollegeMapper collegeMapper;
    
    @Autowired
    private CourseDependenceMapper courseDependenceMapper;

    @Override
    public Result getCourseInfoList(HttpServletRequest httpServletRequest, CourseInfoListReq courseInfoListReq) {
        List<CourseInfo> courseInfoList = courseInfoMapper.getCourseInfoList(courseInfoListReq);
        IPage<CourseInfo> page = PageUtil.getPage(new Page<>(), courseInfoListReq.getPageNo(), courseInfoListReq.getPageSize(), courseInfoList);
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
        for (int i=0; i<courseInfoAddReq.getPreCourseIdList().size(); i++) {
            CourseDependence courseDependence = new CourseDependence();
            courseDependence.setId(SnowflakeUtil.nextId());
            courseDependence.setCourseId(courseInfo.getId());
            courseDependenceMapper.insert(courseDependence);
        }
        courseInfoMapper.insert(courseInfo);
        return Result.success();
    }

    @Override
    public Result removeCourseInfo(HttpServletRequest httpServletRequest, List<String> courseInfoIds) {
        courseInfoMapper.deleteBatchIds(courseInfoIds);
        LambdaQueryWrapper<CourseTimeplace> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CourseTimeplace::getCourseId, courseInfoIds);
        courseTimeplaceMapper.delete(queryWrapper);
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
        
        String id = courseInfoAddReq.getCampus() + (courseInfoAddReq.getCollege().toString().length() == 1 ? "0" : "") + courseInfoAddReq.getCollege().toString() + courseInfoAddReq.getType().toString() + (courseInfoAddReq.getGeneralType() == null ? "0" : String.valueOf(courseInfoAddReq.getGeneralType()+1)) + (num++).toString();
        System.out.println(id);
        courseInfo.setId(id);
        return courseInfo;
    }
}
