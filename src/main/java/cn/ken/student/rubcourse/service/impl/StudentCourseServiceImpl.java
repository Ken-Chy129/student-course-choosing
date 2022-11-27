package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.resp.StudentChooseLogResp;
import cn.ken.student.rubcourse.entity.*;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.service.IStudentCourseService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 学生选课表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Autowired
    private StudentCreditsMapper studentCreditsMapper;
    
    @Autowired
    private CourseDependenceMapper courseDependenceMapper;
    
    @Autowired
    private ClassMapper classMapper;
    
    @Autowired
    private CourseEmergencyMapper courseEmergencyMapper;
    
    @Autowired
    private CourseClassMapper courseClassMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result getStudentChooseLog(HttpServletRequest httpServletRequest, Long studentId, Integer semester, Boolean isChosen) {
        List<StudentChooseLogResp> studentCourse = studentCourseMapper.getStudentChooseLogs(studentId, semester, isChosen);
        return Result.success(studentCourse);
    }

    @Override
    @Transactional
    public Result chooseCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse) {
        String token = httpServletRequest.getHeader(RedisConstant.STUDENT_TOKEN_PREFIX + "token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(token), HashMap.class);
        if (hashMap == null) {
            return Result.fail(ErrorCodeEnums.LOGIN_CREDENTIAL_EXPIRED);
        }
        String studentId = hashMap.get("id");
        Integer classId = Integer.valueOf(hashMap.get("classId"));
        // 查询
        StudentCredits studentCredits = studentCreditsMapper.selectByStudentAndSemester(studentId, studentCourse.getSemester());
        BigDecimal subtract = studentCredits.getMaxSubjectCredit().subtract(studentCredits.getChooseSubjectCredit());
        if (subtract.compareTo(studentCourse.getCredits()) < 0) {
            return Result.fail(ErrorCodeEnums.CREDITS_NOT_ENOUGH);
        }
        List<CourseDependence> courseDependenceList = courseDependenceMapper.selectCourseDependence(studentCourse.getCourseClassId());
        for (CourseDependence courseDependence : courseDependenceList) {
            String preCourseId = courseDependence.getPreCourseId();
            LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StudentCourse::getCourseClassId, preCourseId)
                        .eq(StudentCourse::getStudentId, studentId)
                        .eq(StudentCourse::getIsDeleted, false);
            Long count = studentCourseMapper.selectCount(queryWrapper);
            if (count == 0) {
                return Result.fail(ErrorCodeEnums.PRECOURSE_NOT_CHOOSE);
            }
        }
        List<CourseEmergency> courseEmergencyList = courseEmergencyMapper.selectByCourseId(studentCourse.getCourseClassId());
        Class aClass = classMapper.selectById(classId);
        Integer year = aClass.getYear();
        for (CourseEmergency courseEmergency : courseEmergencyList) {
            if (courseEmergency.getOnlyToClass() != null && !courseEmergency.getOnlyToClass().equals(classId)) {
                return Result.fail(ErrorCodeEnums.CONDITION_NOT_SATISFIED);
            }
            if (courseEmergency.getOnlyToGrade() != null && !courseEmergency.getOnlyToGrade().equals(year)) {
                return Result.fail(ErrorCodeEnums.CONDITION_NOT_SATISFIED);
            }
        }
        studentCourse.setId(SnowflakeUtil.nextId());
        studentCourseMapper.insert(studentCourse);
        studentCredits.setChooseSubjectCredit(subtract);
        studentCreditsMapper.updateById(studentCredits);
        CourseClass courseClass = courseClassMapper.selectById(studentCourse.getCourseClassId());
        courseClass.setChoosingNum(courseClass.getChoosingNum() - 1);
        courseClassMapper.updateById(courseClass);
        return null;
    }
}
