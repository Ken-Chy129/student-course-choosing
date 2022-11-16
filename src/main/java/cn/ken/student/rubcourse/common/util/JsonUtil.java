package cn.ken.student.rubcourse.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/16 23:12
 */
public class JsonUtil {
    
    private static final Gson gson = new Gson();

    public static String toString(Object object) {
        return gson.toJson(object);
    }
}
