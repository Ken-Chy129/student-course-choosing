package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学院表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ICollegeService extends IService<College> {

    Result getCollegeList(HttpServletRequest httpServletRequest);

    Result addCollege(HttpServletRequest httpServletRequest, String collegeName);
}
