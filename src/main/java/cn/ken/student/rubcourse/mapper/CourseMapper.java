package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.dto.req.AllCourseListReq;
import cn.ken.student.rubcourse.dto.resp.ClassCourseListResp;
import cn.ken.student.rubcourse.dto.resp.CourseNameListResp;
import cn.ken.student.rubcourse.entity.Course;
import cn.ken.student.rubcourse.entity.CourseClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程信息表 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    
}
