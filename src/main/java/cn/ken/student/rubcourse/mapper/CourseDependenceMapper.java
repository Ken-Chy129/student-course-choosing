package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.entity.CourseDependence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程依赖表 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface CourseDependenceMapper extends BaseMapper<CourseDependence> {

    List<CourseDependence> selectCourseDependence(String courseId);

    List<CourseDependence> selectAfterCourse(Long courseClassId);
}
