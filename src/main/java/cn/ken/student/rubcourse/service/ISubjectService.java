package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.sys.req.SubjectAddReq;
import cn.ken.student.rubcourse.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 专业表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ISubjectService extends IService<Subject> {

    Result getSubjectList(HttpServletRequest httpServletRequest, Integer departmentId);

    Result addSubject(HttpServletRequest httpServletRequest, SubjectAddReq subjectAddReq);
}
