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
    public Result getStudentCredits(HttpServletRequest httpServletRequest, Integer studentId, Integer semester) {
        LambdaQueryWrapper<StudentCredits> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCredits::getStudentId, studentId)
                .eq(StudentCredits::getSemester, semester);
        return Result.success(studentCreditsMapper.selectOne(queryWrapper));
    }

    @Override
    public Result updateStudentCredits(HttpServletRequest httpServletRequest, StudentCredits studentCredits) {
        return Result.success(studentCreditsMapper.updateById(studentCredits));
    }

    @Override
    public Result getCredit(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(token), HashMap.class);
        if (hashMap == null) {
            return Result.fail(ErrorCodeEnums.LOGIN_CREDENTIAL_EXPIRED);
        }
        
        String id = hashMap.get("id");
        ChooseRound chooseRound = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.PRESENT_ROUND), ChooseRound.class);
        if (chooseRound == null) {
            return Result.fail(ErrorCodeEnums.NO_ROUND_PRESENT);
        }
        
        Integer semester = chooseRound.getSemester();
        LambdaQueryWrapper<StudentCredits> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCredits::getStudentId, id)
                .eq(StudentCredits::getSemester, semester);
        StudentCredits studentCredits = studentCreditsMapper.selectOne(queryWrapper);
        return Result.success(studentCredits);
    }


}
