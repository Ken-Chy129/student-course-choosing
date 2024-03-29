package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.model.entity.CourseTimeplace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程-时间地点表 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface CourseTimeplaceMapper extends BaseMapper<CourseTimeplace> {

    List<CourseTimeplace> selectByCourseClassId(@Param("id") Long id);
}
