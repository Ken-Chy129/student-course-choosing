package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.dto.req.StudentChooseLogReq;
import cn.ken.student.rubcourse.dto.resp.StudentChooseLogResp;
import cn.ken.student.rubcourse.entity.StudentCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 学生选课表 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    
    List<StudentCourse> getStudentCourse(Long id, Integer semester);

    List<StudentChooseLogResp> getStudentChooseLogs(StudentChooseLogReq studentChooseLogReq);
    
    StudentCourse getIsCourseChoose(String courseId, Long studentId, Integer semester);

    StudentCourse selectByStudentAndSemesterAndCourseClass(Long studentId, Integer semester, Long courseClassId);

    List<StudentCourse> selectByCourseClassAndSemester(Long id, Integer semester);
}
