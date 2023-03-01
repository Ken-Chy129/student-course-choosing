package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.config.RabbitMQConfig;
import cn.ken.student.rubcourse.model.dto.MessageDTO;
import cn.ken.student.rubcourse.model.dto.sys.req.SysNoticePageReq;
import cn.ken.student.rubcourse.model.entity.SysNotice;
import cn.ken.student.rubcourse.mapper.SysNoticeMapper;
import cn.ken.student.rubcourse.service.ISysNoticeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 系统通知表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    
    @Override
    public Result sendMessage(HttpServletRequest httpServletRequest, MessageDTO messageDTO) {
        SysNotice sysNotice = new SysNotice();
        Long id = SnowflakeUtil.nextId();
        sysNotice.setId(id);
        sysNotice.setMessage(messageDTO.getMessage());
        sysNotice.setStudentId(messageDTO.getStudentId());
        sysNotice.setStatus((short) 0);
        sysNoticeMapper.insert(sysNotice);
        amqpTemplate.convertAndSend(RabbitMQConfig.NOTICE_EXCHANGE, "", id);
        return Result.success(sysNotice);
    }

    @Override
    public Result sendAnnouncement(HttpServletRequest httpServletRequest, MessageDTO messageDTO) {
        SysNotice sysNotice = new SysNotice();
        Long id = SnowflakeUtil.nextId();
        sysNotice.setId(id);
        sysNotice.setMessage(messageDTO.getMessage());
        sysNotice.setStudentId(-1L);
        sysNotice.setStatus((short) 0);
        sysNoticeMapper.insert(sysNotice);
        amqpTemplate.convertAndSend(RabbitMQConfig.NOTICE_EXCHANGE, "", id);
        return Result.success(sysNotice);
    }

    @Override
    public Result getNoticePage(HttpServletRequest httpServletRequest, SysNoticePageReq sysNoticePageReq) {
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(sysNoticePageReq.getStudentId() != null, SysNotice::getStudentId, sysNoticePageReq.getStudentId());
        List<SysNotice> sysNotices = sysNoticeMapper.selectList(queryWrapper);
        IPage<SysNotice> page = PageUtil.getPage(new Page<>(), sysNoticePageReq.getPageNo(), sysNoticePageReq.getPageSize(), sysNotices);
        return Result.success(page);
    }
}
