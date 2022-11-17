package cn.ken.student.rubcourse.aop;

import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.entity.SysManager;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/17 15:31
 */
@Slf4j
@Aspect
@Component
public class AuthenticationAop {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("execution(* cn.ken.student.rubcourse.service.impl.*.*(..))")
    public void cut() {}
    
    @Before(value = "cut()")
    public void verify(JoinPoint joinPoint) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) joinPoint.getArgs()[0];
//        String token = httpServletRequest.getHeader("token");
        SysManager sysManager = JSON.parseObject(redisTemplate.opsForValue().get("10000"), SysManager.class);
        if (sysManager == null) {
            throw new BusinessException(ErrorCodeEnums.SYS_UN_LOGIN);
        }
        if (sysManager.getType() != 1) {
            throw new BusinessException(ErrorCodeEnums.SYS_NO_PERMISSION);
        }
    }
}
