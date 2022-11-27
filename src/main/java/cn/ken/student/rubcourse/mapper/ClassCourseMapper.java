package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.dto.req.ClassCourseListReq;
import cn.ken.student.rubcourse.dto.ClassInfoDTO;
import cn.ken.student.rubcourse.entity.ClassCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 方案内课程 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface ClassCourseMapper extends BaseMapper<ClassCourse> {
    
//    List<ClassCourse> getClassCourseListByClassId(Integer classId);

    List<ClassInfoDTO> getClassCourseList(ClassCourseListReq classCourseListReq);

}
