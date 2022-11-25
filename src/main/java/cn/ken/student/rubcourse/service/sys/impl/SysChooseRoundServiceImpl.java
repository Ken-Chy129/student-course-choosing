package cn.ken.student.rubcourse.service.sys.impl;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.req.ChooseRoundListReq;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.entity.StudentCredits;
import cn.ken.student.rubcourse.mapper.ChooseRoundMapper;
import cn.ken.student.rubcourse.mapper.StudentCreditsMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.sys.ISysChooseRoundService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 选课轮次 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class SysChooseRoundServiceImpl extends ServiceImpl<ChooseRoundMapper, ChooseRound> implements ISysChooseRoundService {
    
    @Autowired
    private ChooseRoundMapper chooseRoundMapper;
    
    @Autowired
    private StudentCreditsMapper studentCreditsMapper;
    
    @Autowired
    private StudentMapper studentMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result getPresentRound(HttpServletRequest httpServletRequest) {
        // 从缓存中获取
        ChooseRound chooseRound0 = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.PRESENT_ROUND), ChooseRound.class);
        if (chooseRound0 != null) {
            return Result.success(chooseRound0);
        }
        // 缓存中不存在则查库
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<ChooseRound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(ChooseRound::getStartTime, now)
                .gt(ChooseRound::getEndTime, now);
        ChooseRound chooseRound = chooseRoundMapper.selectOne(queryWrapper);
        if (chooseRound == null) {
            return Result.fail(ErrorCodeEnums.NO_ROUND_PRESENT);
        }
        // 库中存在则同时存入缓存，有效时间为当前轮次结束时间
        redisTemplate.opsForValue().set(RedisConstant.PRESENT_ROUND, JSON.toJSONString(chooseRound), Duration.between(LocalDateTime.now(), chooseRound.getEndTime()).toSeconds(), TimeUnit.SECONDS);
        return Result.success(chooseRound);
    }

    @Override
    public Result getRoundList(HttpServletRequest httpServletRequest, ChooseRoundListReq chooseRoundListReq) {
        Integer presentRoundId = chooseRoundListReq.getPresentRoundId();
        Boolean showAll = chooseRoundListReq.getShowAll();
        
        LambdaQueryWrapper<ChooseRound> queryWrapper = new LambdaQueryWrapper<>();
        if (!showAll) {
            Integer prefix = Integer.valueOf(presentRoundId.toString().substring(0, presentRoundId.toString().length() - 1));
            queryWrapper.likeRight(ChooseRound::getId, prefix);
        }
        List<ChooseRound> list = chooseRoundMapper.selectList(queryWrapper);
        
        IPage<ChooseRound> page = PageUtil.getPage(new Page<>(), chooseRoundListReq.getPageNo(), chooseRoundListReq.getPageSize(), list);
        
        return Result.success(page);
    }

    @Override
    @Transactional
    public Result addChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound) {
        if (chooseRound.getStartTime().isAfter(chooseRound.getEndTime())) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_INVALID);
        }
        List<ChooseRound> list = chooseRoundMapper.selectTimePeriod(chooseRound);
        if (list.size() != 0) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_REPEAT);
        }
        Integer id = Integer.valueOf(chooseRound.getSemester().toString() + chooseRound.getRoundNo().toString());
        chooseRound.setId(id);
        
        // 如果是当前学期第一次创建则同时创建学生学分表
        LambdaQueryWrapper<ChooseRound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChooseRound::getSemester, chooseRound.getSemester());
        Long count = chooseRoundMapper.selectCount(queryWrapper);
        if (count == 0) {
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getStatus, 0);
            List<Student> students = studentMapper.selectList(studentLambdaQueryWrapper);
            for (Student student : students) {
                StudentCredits studentCredits = new StudentCredits();
                studentCredits.setId(SnowflakeUtil.nextId());
                studentCredits.setStudentId(student.getId());
                studentCredits.setSemester(chooseRound.getSemester());
                studentCredits.setChooseSubjectCredit(BigDecimal.valueOf(0));
                studentCredits.setMaxSubjectCredit(BigDecimal.valueOf(22));
                studentCreditsMapper.insert(studentCredits);
            }
        }
        chooseRoundMapper.insert(chooseRound);
       
        return Result.success();
    }

    @Override
    public Result updateChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound) {
        if (chooseRound.getStartTime().isAfter(chooseRound.getEndTime())) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_INVALID);
        }
        List<ChooseRound> list = chooseRoundMapper.selectTimePeriod(chooseRound);
        if (list.size() > 1) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_REPEAT);
        } else if (list.size() == 1) {
            if (!list.get(0).getId().equals(chooseRound.getId())) {
                return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_REPEAT);
            }
        }
        try {
            chooseRoundMapper.updateById(chooseRound);
        } catch (Exception e) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_INVALID);
        }
        return Result.success();
    }

    @Override
    public Result removeChooseRound(HttpServletRequest httpServletRequest, Integer chooseRoundId) {
        ChooseRound chooseRound = chooseRoundMapper.selectById(chooseRoundId);
        LambdaQueryWrapper<StudentCredits> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCredits::getSemester, chooseRound.getSemester());
        studentCreditsMapper.delete(queryWrapper);
        chooseRoundMapper.deleteById(chooseRoundId);
        return Result.success();
    }
}
