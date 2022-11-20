package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.entity.StudentCourse;
import cn.ken.student.rubcourse.mapper.StudentCourseMapper;
import cn.ken.student.rubcourse.service.IStudentCourseService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result getStudentChoose(HttpServletRequest httpServletRequest, Long studentId, Integer semester, Boolean isChosen) {
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getStudentId, studentId)
                .eq(semester != null, StudentCourse::getSemester, semester)
                .eq(StudentCourse::getIsDeleted, !isChosen);
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(queryWrapper);
        return Result.success(studentCourses);
    }

    @Override
    public Result chooseCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse) {
        String token = httpServletRequest.getHeader("token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(token), HashMap.class);
        if (hashMap == null) {
            return Result.fail(ErrorCodeEnums.LOGIN_CREDENTIAL_EXPIRED);
        }
        // 查询
        return null;
    }
}
