package cn.ken.student.rubcourse.common.util;

import cn.hutool.crypto.digest.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * <pre>
 * API接口签名验证工具类
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/1 16:13
 */
@Component
public class ApiVerifyUtil {

    public static final String SECRET_KEY = "secretkey";
    public static final String ACCESS_KEY = "accesskey";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String NONCE_KEY = "nonce";
    public static final String SIGN_KEY = "sign";

    private static final HashMap<String, String> KEY_PAIR;
    static {
        KEY_PAIR = new HashMap<>();
        KEY_PAIR.put("app1", "password1"); // 为客户端分配的密钥
        KEY_PAIR.put("app2", "password2");
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplateBean;
    private static RedisTemplate<String, String> redisTemplate;
    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateBean;
    }

    public static final Integer OK = 0;
    public static final Integer PARAMS_ERROR = 1;
    public static final Integer LACK_ACCESSKEY = 2;
    public static final Integer ACCESSKEY_INVALID = 3;
    public static final Integer LACK_NONCE = 4;
    public static final Integer LACK_TIMESTAMP = 5;
    public static final Integer LACK_SIGN = 6;
    public static final Integer REQUEST_TIMEOUT = 7;
    public static final Integer REQUEST_REPEATED = 8;
    public static final Integer REQUEST_INVALID = 9;

    // 超时时间(ms)
    public static final long TIMEOUT = 1000 * 60 * 15;
    public static final String AND = "&";
    public static final String EQUALS = "=";

    public static Integer verify(HashMap<String, String> params) {
        if (params == null || params.isEmpty()) {
            return PARAMS_ERROR;
        }
        // accessKey为空或不合法(即不存在对应的密钥)或请求参数不全则直接打回
        String accessKey, secretKey, timestamp, nonce, sign;
        if ((accessKey = params.get(ACCESS_KEY)) == null) {
            return LACK_ACCESSKEY;
        }
        if ((secretKey = KEY_PAIR.get(accessKey)) == null) {
            return ACCESSKEY_INVALID;
        }
        if ((timestamp = params.get(TIMESTAMP_KEY)) == null) {
            return LACK_TIMESTAMP;
        } else if (Long.parseLong(timestamp) - System.currentTimeMillis() > TIMEOUT) {
            // 超过15分钟
            return REQUEST_TIMEOUT;
        }
        if ((nonce = params.get(NONCE_KEY)) == null) {
            return LACK_NONCE;
        } else {
            Set<String> nonceSet = redisTemplate.opsForSet().members("nonce");
            if (nonceSet != null && nonceSet.contains(nonce)) {
                return REQUEST_REPEATED;
            }
        }
        if ((sign = params.get(SIGN_KEY)) == null) {
            return LACK_SIGN;
        }
        params.remove(SIGN_KEY);
        // 加密需要拼接上密钥
        params.put(SECRET_KEY, secretKey);
        ArrayList<String> keyList = new ArrayList<>(params.keySet());
        // 按顺序构造
        Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        for (String key : keyList) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        String newSign = sb.toString();
        newSign = MD5.create().digestHex16(newSign.substring(0, newSign.length() - 1).toUpperCase());
        if (newSign.equals(sign)) {
            redisTemplate.opsForSet().add("nonce", nonce);
            return OK;
        }
        return REQUEST_INVALID;
    }

    public static HashMap<String, String> getParams(HttpServletRequest httpServletRequest) {
        String queryString = httpServletRequest.getQueryString();
        String[] split = queryString.split(AND);
        HashMap<String, String> params = new HashMap<>();
        for (String param : split) {
            params.put(param.split(EQUALS)[0], param.split(EQUALS)[1]);
        }
        return params;
    }

    public static HashMap<String, String> getParams(String queryString) {
        String[] split = queryString.split(AND);
        HashMap<String, String> params = new HashMap<>();
        for (String param : split) {
            params.put(param.split(EQUALS)[0], param.split(EQUALS)[1]);
        }
        return params;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        // 加密需要拼接secretKey且按顺序
        String toMd5 = "accesskey=app1&nonce=1&param1=1&param2=2&secretkey=password1&timestamp=1677664407639";
        String sign = MD5.create().digestHex16(toMd5.toUpperCase());
        System.out.println(sign);
        // query不携带secretKey
        String query = "accesskey=app1&b=2222&a=1111&nonce=1111&timestamp=2222&sign=" + sign;
        System.out.println(verify(getParams(query)));
    }
}
