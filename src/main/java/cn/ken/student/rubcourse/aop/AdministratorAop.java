package cn.ken.student.rubcourse.aop;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.IpUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.common.util.StringUtils;
import cn.ken.student.rubcourse.entity.SysBackendLog;
import cn.ken.student.rubcourse.entity.SysFrontendLog;
import cn.ken.student.rubcourse.mapper.SysBackendLogMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/25 16:46
 */
@Slf4j
@Aspect
@Component
public class AdministratorAop {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private SysBackendLogMapper sysBackendLogMapper;

    @Pointcut("@annotation(cn.ken.student.rubcourse.annotation.Administrator)")
    public void administratorCut() {}

    @Before(value = "administratorCut()")
    public void administratorVerify(JoinPoint joinPoint) throws BusinessException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) joinPoint.getArgs()[0];
        String token = httpServletRequest.getHeader("token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.SYSTEM_TOKEN_PREFIX + token), HashMap.class);
        if (hashMap == null) {
            throw new BusinessException(ErrorCodeEnums.SYS_UN_LOGIN);
        }
        String type = hashMap.get("type");
        if (type.equals("0")) {
            String ipAddr = IpUtil.getIpAddr(httpServletRequest);
            SysBackendLog sysBackendLog = new SysBackendLog(SnowflakeUtil.nextId(), 1, ipAddr, Integer.valueOf(hashMap.get("id")), joinPoint.getSignature().getName(), StringUtils.toString(Arrays.toString(joinPoint.getArgs())), ErrorCodeEnums.SYS_NO_PERMISSION.getDesc());
            sysBackendLogMapper.insert(sysBackendLog);
            throw new BusinessException(ErrorCodeEnums.SYS_NO_PERMISSION);
        }
    }

}
