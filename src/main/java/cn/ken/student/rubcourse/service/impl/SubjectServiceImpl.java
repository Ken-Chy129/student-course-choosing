package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.sys.req.SubjectAddReq;
import cn.ken.student.rubcourse.model.entity.Subject;
import cn.ken.student.rubcourse.mapper.SubjectMapper;
import cn.ken.student.rubcourse.service.ISubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 专业表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Autowired
    private SubjectMapper subjectMapper;
    
    @Override
    public Result getSubjectList(HttpServletRequest httpServletRequest, Integer departmentId) {
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subject::getDepartmentId, departmentId);
        List<Subject> subjects = subjectMapper.selectList(queryWrapper);
        return Result.success(subjects);
    }

    @Override
    public Result addSubject(HttpServletRequest httpServletRequest, SubjectAddReq subjectAddReq) {
        Subject subject = new Subject();
        subject.setDepartmentId(subjectAddReq.getDepartmentId());
        subject.setSubjectName(subjectAddReq.getSubjectName());
        subjectMapper.insert(subject);
        return Result.success(subject);
    }
}
