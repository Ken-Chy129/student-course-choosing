package cn.ken.student.rubcourse.aop;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.IpUtil;
import cn.ken.student.rubcourse.common.util.StringUtils;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.entity.SysBackendLog;
import cn.ken.student.rubcourse.entity.SysFrontendLog;
import cn.ken.student.rubcourse.mapper.SysBackendLogMapper;
import cn.ken.student.rubcourse.mapper.SysFrontendLogMapper;
import com.alibaba.fastjson.JSON;
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
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

//    @Pointcut("execution(* cn.ken.student.rubcourse.service.sys.impl.*.*(..)) && !execution(* cn.ken.student.rubcourse.service.impl.SysManagerServiceImpl.login(..))")
//    public void backendCut() {}
//
//    @Pointcut("execution(* cn.ken.student.rubcourse.service.impl.*.*(..)) && !execution(* cn.ken.student.rubcourse.service.impl.StudentServiceImpl.login(..))")
//    public void frontendCut() {}

    @Pointcut("execution(* cn.ken.student.rubcourse.controller.sys.*.*(..)) && !execution(* cn.ken.student.rubcourse.controller.sys.SysManagerController.login(..)) && !execution(* cn.ken.student.rubcourse.controller.sys.SysCommonController.*(..)) ")
    public void backendCut() {}

    @Pointcut("execution(* cn.ken.student.rubcourse.controller.frontend.*.*(..)) && !execution(* cn.ken.student.rubcourse.controller.frontend.CommonController.*(..)) && !execution(* cn.ken.student.rubcourse.controller.frontend.LoginController.*(..))")
    public void frontendCut() {}

    @Around(value = "backendCut()")
    public Object backendAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long id = SnowflakeUtil.nextId();
        String methodName = proceedingJoinPoint.getSignature().getName(); // 请求方法名
        HttpServletRequest httpServletRequest = (HttpServletRequest) proceedingJoinPoint.getArgs()[0];
        String token = httpServletRequest.getHeader("token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.SYSTEM_TOKEN_PREFIX + token), HashMap.class);
        redisTemplate.opsForValue().set(RedisConstant.SYSTEM_TOKEN_PREFIX + token, JSON.toJSONString(hashMap), 86400, TimeUnit.SECONDS);
        if (hashMap == null) {
            throw new BusinessException(ErrorCodeEnums.SYS_UN_LOGIN);
        }
        String ipAddr = IpUtil.getIpAddr((HttpServletRequest) proceedingJoinPoint.getArgs()[0]);
        SysBackendLog sysBackendLog = new SysBackendLog(id, 0, ipAddr, Integer.valueOf(hashMap.get("id")), methodName, StringUtils.toString(Arrays.toString(proceedingJoinPoint.getArgs())), null);
        Object result;
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
        HttpServletRequest httpServletRequest = (HttpServletRequest) proceedingJoinPoint.getArgs()[0];
        String token = httpServletRequest.getHeader("token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.STUDENT_TOKEN_PREFIX + token), HashMap.class);
        redisTemplate.opsForValue().set(RedisConstant.STUDENT_TOKEN_PREFIX + token, JSON.toJSONString(hashMap), 86400, TimeUnit.SECONDS);
        if (hashMap == null) {
            throw new BusinessException(ErrorCodeEnums.LOGIN_CREDENTIAL_EXPIRED);
        }
        String ipAddr = IpUtil.getIpAddr(httpServletRequest);
        SysFrontendLog sysFrontendLog = new SysFrontendLog(id, 0, ipAddr, Integer.valueOf(hashMap.get("id")), methodName, StringUtils.toString(Arrays.toString(proceedingJoinPoint.getArgs())), null);
        Object result;
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
