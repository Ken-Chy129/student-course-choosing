package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.dto.req.ClassListReq;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.entity.Subject;
import cn.ken.student.rubcourse.mapper.ClassMapper;
import cn.ken.student.rubcourse.mapper.SubjectMapper;
import cn.ken.student.rubcourse.service.IClassService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    @Autowired
    private ClassMapper classMapper;
    
    @Autowired
    private SubjectMapper subjectMapper;
    
    @Override
    public Result getClassList(HttpServletRequest httpServletRequest, ClassListReq classListReq) {
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(classListReq.getYear() != null, Class::getYear, classListReq.getYear())
                    .eq(classListReq.getCollegeName() != null, Class::getCollegeName, classListReq.getCollegeName())
                    .eq(classListReq.getDepartmentName() != null, Class::getDepartmentName, classListReq.getDepartmentName())
                    .eq(classListReq.getSubjectName() != null, Class::getSubjectName, classListReq.getSubjectName())
                    .like(classListReq.getSearchContent() != null, Class::getClassName, classListReq.getSearchContent());
        List<Class> classList = classMapper.selectList(queryWrapper);
        IPage<Class> page = PageUtil.getPage(new Page<>(), classListReq.getPageNo(), classListReq.getPageSize(), classList);
        return Result.success(page);
    }

    @Override
    public Result addClass(HttpServletRequest httpServletRequest, Class clazz) {
        LambdaQueryWrapper<Subject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Subject::getSubjectName, clazz.getSubjectName());
        Integer subjectId = subjectMapper.selectOne(lambdaQueryWrapper).getId();
        String id = clazz.getYear().toString().substring(2) + String.format("%05d", subjectId) + clazz.getClassNo().toString();
        clazz.setId(Integer.valueOf(id));
        classMapper.insert(clazz);
        return Result.success(clazz);
    }

}
