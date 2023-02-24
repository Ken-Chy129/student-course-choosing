package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.entity.StudentCredits;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学生学分表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IStudentCreditsService extends IService<StudentCredits> {

    Result getStudentCredits(HttpServletRequest httpServletRequest, Integer studentId);

    Result updateStudentCredits(HttpServletRequest httpServletRequest, StudentCredits studentCredits);

    Result getStudentCreditOnSemester(HttpServletRequest httpServletRequest, Integer studentId, Integer semester);
}
