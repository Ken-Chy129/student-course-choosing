package cn.ken.student.rubcourse.websocket;

import cn.ken.student.rubcourse.entity.SysNotice;
import cn.ken.student.rubcourse.mapper.SysFrontendLogMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/22 19:36
 */
@Slf4j
@Component
@ServerEndpoint(value = "/notice/{studentId}")
public class WebSocketServer {

    /**
     * 用于存放所有在线客户端
     */
    private static final Map<Long, Session> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    
    private Long key;
    
    public static Map<Long, Session> getSessionMap(){
        return SESSION_MAP;
    }
    
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("studentId") Long studentId) {
        this.session = session;
        this.key = studentId;
        log.info("有新的客户端上线: {}", studentId);
        SESSION_MAP.put(studentId, this.session);
        HashMap<String, Integer> loginNum = new LinkedHashMap<>();
        loginNum.put("num", getSessionMap().size());
        for(Long sessionId : SESSION_MAP.keySet()) {
            SESSION_MAP.get(sessionId).getAsyncRemote().sendText(JSON.toJSONString(loginNum));
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("有客户端离线: {}", this.key);
        SESSION_MAP.remove(this.key);
        HashMap<String, Integer> loginNum = new LinkedHashMap<>();
        loginNum.put("num", getSessionMap().size());
        for(Long sessionId : SESSION_MAP.keySet()) {
            SESSION_MAP.get(sessionId).getAsyncRemote().sendText(JSON.toJSONString(loginNum));
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message)  {
//        JSONObject object  = JSON.parseObject(message);
//        MessageDTO msgDTO = JSON.toJavaObject(object, MessageDTO.class);
//        if (ObjectUtils.isEmpty(msgDTO) || !StringUtils.hasText(msgDTO.getMessage())){
//            return;
//        }
//        String msg = JSON.toJSONString(msgDTO);
//        log.info("消息: {}", message);
//        msg = msg.replace("\\t", "").replace("\\n", "");
//        amqpTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", message);
//        log.info("{}", "消息为空，不进行发送");
    }

    /**
     * @param session 消息
     * @param error 异常
     */
    @OnError
    public void onError(Session session, Throwable error) {
        String sessionId = session.getId();
        if (SESSION_MAP.get(Long.valueOf(sessionId)) != null) {
            log.info("发生了错误,移除客户端: {}", sessionId);
            SESSION_MAP.remove(Long.valueOf(sessionId));
        }
        log.error("发生异常：",error);
        this.session.getAsyncRemote().sendText(String.valueOf(WebSocketServer.getSessionMap().size()));
    }

    /**
     * 单发 1对1
     */
    public static boolean send(SysNotice sysNotice) {
        log.info("发送给用户: {}", sysNotice.getStudentId());
        if (SESSION_MAP.containsKey(sysNotice.getStudentId())) {
            SESSION_MAP.get(sysNotice.getStudentId()).getAsyncRemote().sendText(JSON.toJSONString(sysNotice));
            log.info("消息发送成功");
            return true;
        }
        log.info("本服务器中无此用户session");
        return false;
    }
    
    /**
     * 群发公告
     */
    public static void batchSend(SysNotice sysNotice) {
        for(Long sessionId : SESSION_MAP.keySet()) {
            SESSION_MAP.get(sessionId).getAsyncRemote().sendText(JSON.toJSONString(sysNotice));
        }
    }
    
}
