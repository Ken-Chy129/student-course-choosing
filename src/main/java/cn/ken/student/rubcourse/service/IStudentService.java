package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.dto.req.StudentLoginReq;
import cn.ken.student.rubcourse.dto.req.StudentOnConditionReq;
import cn.ken.student.rubcourse.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IStudentService extends IService<Student> {

    Result addStudent(HttpServletRequest httpServletRequest, Student student) throws Exception;

    Result getStudentById(HttpServletRequest httpServletRequest, Long id) throws Exception;

    Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentOnConditionReq studentOnConditionReq) throws Exception;
    
    Result login(HttpServletRequest httpServletRequest, StudentLoginReq studentLoginReq);

    Result getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Long studentId) throws IOException;

    Result logout(HttpServletRequest httpServletRequest, Long token);

    Result batchAddStudent(HttpServletRequest httpServletRequest, MultipartFile file) throws IOException;

    Result getGithubUrl(HttpServletRequest httpServletRequest);

    Result githubCallback(HttpServletRequest httpServletRequest, String code, String state) throws Exception;
}
