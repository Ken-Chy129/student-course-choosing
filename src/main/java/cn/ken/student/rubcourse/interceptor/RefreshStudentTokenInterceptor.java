package cn.ken.student.rubcourse.interceptor;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.UserHolder;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 拦截所有请求，放行所有用户，需要登录的请求会在下一个拦截器进行拦截
 * 主要用于刷新token，让登录了的用户执行不需要登录凭证的接口也可以刷新token有效期
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/21 23:18
 */
public class RefreshStudentTokenInterceptor implements HandlerInterceptor {

    private RedisTemplate<String, String> redisTemplate;

    public RefreshStudentTokenInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String token = request.getHeader("token");
        // 无携带凭证，未登录
        if (token == null) {
            return true;
        }
        String tokenKey = redisTemplate.opsForValue().get(RedisConstant.STUDENT_TOKEN_PREFIX + token);
        HashMap<String, String> hashMap = JSON.parseObject(tokenKey, HashMap.class);
        // 登录但凭证已过期
        if (hashMap == null) {
            return true;
        }
        // 保存用户信息
        UserHolder.set(hashMap);
        redisTemplate.expire(tokenKey, 30, TimeUnit.MINUTES);
        return true;
    }
}
