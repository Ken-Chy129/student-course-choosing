package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.MessageDTO;
import cn.ken.student.rubcourse.model.dto.sys.req.SysNoticePageReq;
import cn.ken.student.rubcourse.model.entity.SysNotice;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统通知表 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ISysNoticeService extends IService<SysNotice> {
    
    Result sendMessage(HttpServletRequest httpServletRequest, MessageDTO messageDTO);
    
    Result sendAnnouncement(HttpServletRequest httpServletRequest, MessageDTO messageDTO);

    Result getNoticePage(HttpServletRequest httpServletRequest, SysNoticePageReq sysNoticePageReq);
}
