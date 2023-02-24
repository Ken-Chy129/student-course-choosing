package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.model.entity.StudentCredits;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生学分表 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface StudentCreditsMapper extends BaseMapper<StudentCredits> {

    StudentCredits selectByStudentAndSemester(Long studentId, Integer semester);
}
