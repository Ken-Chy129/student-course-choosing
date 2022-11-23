package cn.ken.student.rubcourse.aop;

import cn.ken.student.rubcourse.common.util.IpUtil;
import cn.ken.student.rubcourse.common.util.StringUtils;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.entity.SysBackendLog;
import cn.ken.student.rubcourse.entity.SysFrontendLog;
import cn.ken.student.rubcourse.mapper.SysBackendLogMapper;
import cn.ken.student.rubcourse.mapper.SysFrontendLogMapper;
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
import java.util.Objects;

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
    private SysFrontendLogMapper sysFrontendLogMapper;
    
    @Autowired
    private SysBackendLogMapper sysBackendLogMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Pointcut("execution(* cn.ken.student.rubcourse.service.sys.impl.*.*(..))")
    public void backendCut() {}

    @Pointcut("execution(* cn.ken.student.rubcourse.service.impl.*.*(..))")
    public void frontendCut() {}
    
    @Around(value = "backendCut()")
    public Object backendAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long id = SnowflakeUtil.nextId();
        String methodName = proceedingJoinPoint.getSignature().getName(); // 请求方法名
        // todo: 更改成通过header获取token到redis查询
        Integer studentId = Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("10001")));
        String ipAddr = IpUtil.getIpAddr((HttpServletRequest) proceedingJoinPoint.getArgs()[0]);
        SysBackendLog sysBackendLog = new SysBackendLog(id, 0, ipAddr, studentId, methodName, StringUtils.toString(Arrays.toString(proceedingJoinPoint.getArgs())), null);
        Object result = null;
        try{
            result = proceedingJoinPoint.proceed();
            sysBackendLog.setResponseBody(StringUtils.toString(result));
        } catch (Throwable e) {
            log.error(e.getMessage());
            sysBackendLog.setResponseBody(StringUtils.toString(e.getMessage()));
            sysBackendLog.setType(1);
            sysBackendLogMapper.insert(sysBackendLog);
            throw e;
        }
        sysBackendLogMapper.insert(sysBackendLog);
        return result;
    }

    @Around(value = "frontendCut()")
    public Object frontendAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long id = SnowflakeUtil.nextId();
        String methodName = proceedingJoinPoint.getSignature().getName(); // 请求方法名
        // todo: 更改成通过header获取token到redis查询
        Integer studentId = Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("10001")));
        String ipAddr = IpUtil.getIpAddr((HttpServletRequest) proceedingJoinPoint.getArgs()[0]);
        SysFrontendLog sysFrontendLog = new SysFrontendLog(id, 0, ipAddr, studentId, methodName, StringUtils.toString(Arrays.toString(proceedingJoinPoint.getArgs())), null);
        Object result = null;
        try{
            result = proceedingJoinPoint.proceed();
            sysFrontendLog.setResponseBody(StringUtils.toString(result));
        } catch (Throwable e) {
            log.error(e.getMessage());
            sysFrontendLog.setResponseBody(StringUtils.toString(e.getMessage()));
            sysFrontendLog.setType(1);
            sysFrontendLogMapper.insert(sysFrontendLog);
            throw e;
        }
        sysFrontendLogMapper.insert(sysFrontendLog);
        return result;
    }
}
