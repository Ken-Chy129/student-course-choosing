package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.req.ClassListReq;
import cn.ken.student.rubcourse.model.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface IClassService extends IService<Class> {

    Result getClassList(HttpServletRequest httpServletRequest, ClassListReq classListReq);

    Result addClass(HttpServletRequest httpServletRequest, Class clazz);

}
