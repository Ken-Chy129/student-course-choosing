package cn.ken.student.rubcourse.common.constant;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/21 0:07
 */
public class RedisConstant {
    
    public static String LOCK = "lock:";
    
    /**
     * 空缓存过期时间（单位分钟）
     */
    public static long CACHE_NULL_TTL = 2;
    
    public static String CHECK_CODE_PREFIX = "code:";
    
    public static String PRESENT_ROUND = "present_round";

    public static String SYSTEM_TOKEN_PREFIX = "sys:";
    
    public static String STUDENT_TOKEN_PREFIX = "student:";
    
    public static String COURSE_NUM = "course_num";
    
}
