package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.sys.req.SysLogPageReq;
import cn.ken.student.rubcourse.entity.SysFrontendLog;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前台日志表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ISysFrontendLogService extends IService<SysFrontendLog> {

    Result getFrontendLogPage(HttpServletRequest httpServletRequest, SysLogPageReq sysLogPageReq);
}
