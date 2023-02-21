package cn.ken.student.rubcourse.interceptor;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.entity.UserHolder;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import com.alibaba.fastjson.JSON;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * <pre>
 * <p>拦截需要登录的接口，根据ThreadLocal中是否存有值来判断用户是否登录</p>
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2022/12/10 14:40
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        HashMap<String, String> userMap = UserHolder.get();
        if (userMap == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JSON.toJSONString(Result.fail(ErrorCodeEnums.SYS_UN_LOGIN)));
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
