package cn.ken.student.rubcourse.common.entity;

import java.util.HashMap;

/**
 * <pre>
 * 用ThreadLocal保存登录用户的信息
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/22 0:03
 */
public class UserHolder {
    
    private static final ThreadLocal<HashMap<String, String>> THREAD_LOCAL = new ThreadLocal<>();

    public static HashMap<String, String> get() {
        return THREAD_LOCAL.get();
    }
    
    public static void set(HashMap<String, String> userMap) {
        THREAD_LOCAL.set(userMap);
    }
    
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
