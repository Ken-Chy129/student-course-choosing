package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.sys.req.SysLogPageReq;
import cn.ken.student.rubcourse.model.entity.SysBackendLog;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 后台日志表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ISysBackendLogService extends IService<SysBackendLog> {

    Result getBackendLogPage(HttpServletRequest httpServletRequest, SysLogPageReq sysLogPageReq);
}
