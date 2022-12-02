package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.entity.StudentCredits;
import cn.ken.student.rubcourse.mapper.ChooseRoundMapper;
import cn.ken.student.rubcourse.mapper.StudentCreditsMapper;
import cn.ken.student.rubcourse.service.IStudentCreditsService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 * 学生学分表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class StudentCreditsServiceImpl extends ServiceImpl<StudentCreditsMapper, StudentCredits> implements IStudentCreditsService {

    @Autowired
    private StudentCreditsMapper studentCreditsMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public Result getStudentCredits(HttpServletRequest httpServletRequest, Integer studentId) {
        LambdaQueryWrapper<StudentCredits> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCredits::getStudentId, studentId);
        return Result.success(studentCreditsMapper.selectList(queryWrapper));
    }

    @Override
    public Result updateStudentCredits(HttpServletRequest httpServletRequest, StudentCredits studentCredits) {
        return studentCreditsMapper.updateById(studentCredits) > 0 ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @Override
    public Result getStudentCreditOnSemester(HttpServletRequest httpServletRequest, Integer studentId, Integer semester) {
        LambdaQueryWrapper<StudentCredits> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCredits::getStudentId, studentId)
                .eq(StudentCredits::getSemester, semester);
        return Result.success(studentCreditsMapper.selectOne(queryWrapper));
    }

}
