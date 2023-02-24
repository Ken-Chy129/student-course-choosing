package cn.ken.student.rubcourse.listener;

import cn.ken.student.rubcourse.config.RabbitMQConfig;
import cn.ken.student.rubcourse.model.entity.SysNotice;
import cn.ken.student.rubcourse.mapper.SysNoticeMapper;
import cn.ken.student.rubcourse.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/22 20:55
 */
@Slf4j
@Service
public class RabbitMsgListener {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @RabbitListener(bindings={
        // @QueueBinding注解要完成队列和交换机的
        @QueueBinding(
            // @Queue创建一个队列（没有指定参数则表示创建一个随机队列）
            value=@Queue(),
            // 创建一个交换机
            exchange=@Exchange(name= RabbitMQConfig.FANOUT_EXCHANGE, type="fanout")
        )
    })
    public void fanoutReceive(Long id) {
        SysNotice sysNotice = sysNoticeMapper.selectById(id);
        if (sysNotice.getStudentId() == -1) {
            WebSocketServer.batchSend(sysNotice);
        } else {
            WebSocketServer.send(sysNotice);
        }
        sysNoticeMapper.updateById(sysNotice);
        log.info("{}", "监听并推送消息");
    }
}
