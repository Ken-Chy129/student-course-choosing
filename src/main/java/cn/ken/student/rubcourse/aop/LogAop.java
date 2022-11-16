package cn.ken.student.rubcourse.aop;

import cn.ken.student.rubcourse.common.util.IpUtil;
import cn.ken.student.rubcourse.common.util.JsonUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.entity.SysLog;
import cn.ken.student.rubcourse.mapper.SysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/13 20:17
 */
@Slf4j
@Aspect
@Component
public class LogAop {
    
    @Autowired
    private SysLogMapper sysRequestLogMapper;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Pointcut("execution(* cn.ken.student.rubcourse.service.impl.*.*(..))")
    public void cut() {}
    
    @Around(value = "cut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Long id = SnowflakeUtil.nextId();
        String methodName = proceedingJoinPoint.getSignature().getName(); // 请求方法名
        // todo: 更改成通过header获取token到redis查询
        Integer studentId = (Integer) redisTemplate.opsForValue().get("10001");
        String ipAddr = IpUtil.getIpAddr((HttpServletRequest) proceedingJoinPoint.getArgs()[0]);
        SysLog sysLog = new SysLog(id, 0, ipAddr, studentId, methodName, JsonUtil.toString(Arrays.toString(proceedingJoinPoint.getArgs())), null);
        
        Object result = null;
        try{
            result = proceedingJoinPoint.proceed();
            sysLog.setResponseBody(JsonUtil.toString(result));
        } catch (Throwable e) {
            log.error(e.getMessage());
            sysLog.setResponseBody(JsonUtil.toString(e));
            sysLog.setType(1);
        } finally {
            sysRequestLogMapper.insert(sysLog);
        }
        
        return result;
    }
}
