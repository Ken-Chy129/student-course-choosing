package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.ChooseRoundListReq;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.entity.StudentCredits;
import cn.ken.student.rubcourse.mapper.ChooseRoundMapper;
import cn.ken.student.rubcourse.mapper.StudentCreditsMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IChooseRoundService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 选课轮次 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class ChooseRoundServiceImpl extends ServiceImpl<ChooseRoundMapper, ChooseRound> implements IChooseRoundService {
    
    @Autowired
    private ChooseRoundMapper chooseRoundMapper;
    
    @Autowired
    private StudentCreditsMapper studentCreditsMapper;
    
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Result getPresentRound(HttpServletRequest httpServletRequest) {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<ChooseRound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(ChooseRound::getStartTime, now)
                .gt(ChooseRound::getEndTime, now);
        ChooseRound chooseRound = chooseRoundMapper.selectOne(queryWrapper);
        return Result.success(Objects.requireNonNullElse(chooseRound, "当前不在选课轮次内"));
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
        try {
            chooseRoundMapper.insert(chooseRound);
        } catch (Exception e) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_INVALID);
        }
        // 新增学生学分
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
