package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.constant.WeekDayConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.CourseUtil;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.dto.resp.CourseClassInfoResp;
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
    private CourseUtil courseUtil;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public Result getAllCourseInfoPage(HttpServletRequest httpServletRequest, CourseClassListReq courseClassListReq) {
        
        // 获取学生已选课程表
        List<StudentCourse> studentCourses = studentCourseMapper.getStudentCourse(courseClassListReq.getStudentId(), courseClassListReq.getSemester());

        // 获取课程表
        List<CourseClassInfoResp> courseInfoPage = courseClassMapper.getCourseInfoPage(courseClassListReq);
        
        courseUtil.setPlaceTimeAndIsConflict(courseInfoPage, studentCourses);
        
        IPage<CourseClassInfoResp> page = PageUtil.getPage(new Page<>(), courseClassListReq.getPageNo(), courseClassListReq.getPageSize(), courseInfoPage);
        
        return Result.success(page);
    }

}

