package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.sys.req.ManagerLoginReq;
import cn.ken.student.rubcourse.entity.SysManager;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统管理员表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ISysManagerService extends IService<SysManager> {

    Result login(HttpServletRequest httpServletRequest, ManagerLoginReq managerLoginReq);

    Result logout(HttpServletRequest httpServletRequest, Long token);

    Result getManagerList(HttpServletRequest httpServletRequest);

    Result addManager(HttpServletRequest httpServletRequest, SysManager sysManager);

    Result updateManager(HttpServletRequest httpServletRequest, SysManager sysManager);
}
