package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.req.ClassCourseListReq;
import cn.ken.student.rubcourse.dto.resp.ClassCourseListResp;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.entity.ClassCourse;
import cn.ken.student.rubcourse.entity.StudentCourse;
import cn.ken.student.rubcourse.mapper.ClassCourseMapper;
import cn.ken.student.rubcourse.mapper.CourseClassMapper;
import cn.ken.student.rubcourse.mapper.StudentCourseMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IClassCourseService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private StudentMapper studentMapper;
    
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
    public Result removeClassCourse(HttpServletRequest httpServletRequest, List<Integer> ids) {
        LambdaUpdateWrapper<ClassCourse> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(ClassCourse::getId, ids)
                .set(ClassCourse::getIsDeleted, true);
        classCourseMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result getRecommendedCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {
        
        return null;
    }


    @Override
    public Result getClassCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {
        // 获取轮次信息
        ChooseRound chooseRound = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.PRESENT_ROUND), ChooseRound.class);
        if (chooseRound == null) {
            return Result.fail(ErrorCodeEnums.NO_ROUND_PRESENT);
        }
        
        // 根据条件获取方案内课程
        List<ClassCourseListResp> classCourseList = classCourseMapper.getClassCourseList(classCourseListReq);
        for (ClassCourseListResp classCourseListResp : classCourseList) {
            
        }
        IPage<ClassCourseListResp> page = PageUtil.getPage(new Page<>(), classCourseListReq.getPageNo(), classCourseListReq.getPageSize(), classCourseList);
        return Result.success(page);
    }

}
