package cn.ken.student.rubcourse.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/12/10 14:40
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JSON.toJSONString(Result.fail(ErrorCodeEnums.SYS_UN_LOGIN)));
            return false;
        }
        RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) SpringContextUtil.getBean("redisTemplate");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.SYSTEM_TOKEN_PREFIX + token), HashMap.class);
        if (hashMap == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JSON.toJSONString(Result.fail(ErrorCodeEnums.SYS_UN_LOGIN)));
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
}
