package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.MessageDTO;
import cn.ken.student.rubcourse.dto.sys.req.SysNoticePageReq;
import cn.ken.student.rubcourse.service.ISysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统通知表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/notice")
@Api(tags = "系统通知")
public class SysNoticeController {
    
    @Autowired
    private ISysNoticeService sysNoticeService;
    
    @PostMapping("sendMessage")
    @ApiOperation("发送消息")
    public Result sendNotice(HttpServletRequest httpServletRequest, @RequestBody MessageDTO messageDTO) {
        return sysNoticeService.sendMessage(httpServletRequest, messageDTO);
    }

    @PostMapping("sendAnnouncement")
    @ApiOperation("发送公告")
    public Result sendAnnouncement(HttpServletRequest httpServletRequest, @RequestBody MessageDTO messageDTO) {
        return sysNoticeService.sendAnnouncement(httpServletRequest, messageDTO);
    }
    
    @GetMapping("page")
    @ApiOperation("查看已发送的通知")
    public Result getNoticePage(HttpServletRequest httpServletRequest, SysNoticePageReq sysNoticePageReq) {
        return sysNoticeService.getNoticePage(httpServletRequest, sysNoticePageReq);
    }
}
