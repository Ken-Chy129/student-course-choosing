package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.entity.ClassCourse;
import cn.ken.student.rubcourse.mapper.ClassCourseMapper;
import cn.ken.student.rubcourse.service.IClassCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
    
    @Override
    public Result addClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse) {
        classCourse.setId(SnowflakeUtil.nextId());
        classCourseMapper.insert(classCourse);
        return Result.success();
    }
}
