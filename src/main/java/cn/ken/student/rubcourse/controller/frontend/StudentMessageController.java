package cn.ken.student.rubcourse.controller.frontend;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.sys.req.SysNoticePageReq;
import cn.ken.student.rubcourse.service.ISysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/12/4 19:15
 */
@RestController
@RequestMapping("/studentMessage")
@Api(tags = "学生消息")
public class StudentMessageController {

    @Autowired
    private ISysNoticeService sysNoticeService;
    
    @GetMapping("page")
    @ApiOperation("查看已发送的通知")
    public Result getNoticePage(HttpServletRequest httpServletRequest, SysNoticePageReq sysNoticePageReq) {
        return sysNoticeService.getNoticePage(httpServletRequest, sysNoticePageReq);
    }
}
