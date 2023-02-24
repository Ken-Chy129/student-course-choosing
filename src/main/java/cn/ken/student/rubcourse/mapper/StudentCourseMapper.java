package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.model.dto.req.StudentChooseLogReq;
import cn.ken.student.rubcourse.model.dto.resp.StudentChooseLogResp;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
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
    
    // 查询某个学生某个学期是否已经选择了某门课
    StudentCourse getIsCourseClassChoose(Long courseClassId, Long studentId, Integer semester);

    StudentCourse selectByStudentAndSemesterAndCourseClass(Long studentId, Integer semester, Long courseClassId);

    // 查找某个学期选了某门课的所有学生选课记录
    List<StudentCourse> selectByCourseClassAndSemester(Long id, Integer semester);
}
