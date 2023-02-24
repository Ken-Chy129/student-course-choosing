package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.model.dto.resp.CourseNameListResp;
import cn.ken.student.rubcourse.model.dto.sys.req.CoursePageReq;
import cn.ken.student.rubcourse.model.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<CourseNameListResp> getCourseList(@Param("searchContent") String searchContent);

    List<Course> getCoursePage(CoursePageReq coursePageReq);
}
