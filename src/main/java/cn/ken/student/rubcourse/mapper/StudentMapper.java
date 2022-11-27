package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.dto.req.StudentOnConditionReq;
import cn.ken.student.rubcourse.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    List<Student> selectByCondition(StudentOnConditionReq studentOnConditionReq);

    List<Student> selectByClassId(Long classId);
}
