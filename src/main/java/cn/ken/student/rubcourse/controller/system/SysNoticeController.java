package cn.ken.student.rubcourse.controller.system;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.MessageDTO;
import cn.ken.student.rubcourse.entity.SysNotice;
import cn.ken.student.rubcourse.service.ISysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
    public Result sendNotice(HttpServletRequest httpServletRequest, MessageDTO messageDTO) {
        return sysNoticeService.sendMessage(httpServletRequest, messageDTO);
    }

    @PostMapping("sendAnnouncement")
    @ApiOperation("发送公告")
    public Result sendAnnouncement(HttpServletRequest httpServletRequest, String announcement) {
        return sysNoticeService.sendAnnouncement(httpServletRequest, announcement);
    }
    
}
