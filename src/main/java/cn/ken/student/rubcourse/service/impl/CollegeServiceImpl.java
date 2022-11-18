package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.College;
import cn.ken.student.rubcourse.mapper.CollegeMapper;
import cn.ken.student.rubcourse.service.ICollegeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 学院表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements ICollegeService {

    @Autowired
    private CollegeMapper collegeMapper;
    
    @Override
    public Result getCollegeList(HttpServletRequest httpServletRequest) {
        List<College> colleges = collegeMapper.selectList(new QueryWrapper<>());
        return Result.success(colleges);
    }

    @Override
    public Result addCollege(HttpServletRequest httpServletRequest, String collegeName) {
        College college = new College();
        college.setCollegeName(collegeName);
        return collegeMapper.insert(college) > 0 ? Result.success() : Result.fail();
    }
}
