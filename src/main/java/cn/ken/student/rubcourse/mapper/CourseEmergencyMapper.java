package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.model.entity.CourseEmergency;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程应急设置 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface CourseEmergencyMapper extends BaseMapper<CourseEmergency> {

    List<CourseEmergency> selectByCourseId(Long courseId);
}
