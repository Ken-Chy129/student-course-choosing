package cn.ken.student.rubcourse.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * http请求工具包
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/11 15:01
 */
public class HttpClientUtils {

    /**
     * 使用HttpClient发送一个Get方式的请求
     *
     * @param url 请求的路径 请求参数拼接到url后面
     * @return 响应的数据
     */
    public static String doGet(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 发送一个http请求
        CloseableHttpResponse response = httpclient.execute(httpGet);
        // 如果响应成功,解析响应结果
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 获取响应的内容
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        }
        return null;
    }

    /**
     * 使用HttpClient发送一个带请求头的Get方式的请求
     * @param url 请求的路径 请求参数拼接到url后面
     * @param headers 需要添加的请求头
     * @return 响应的数据
     */
    public static String doGetWithHeaders(String url, Map<String, String> headers) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
            httpGet.addHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        // 发送一个http请求
        CloseableHttpResponse response = httpclient.execute(httpGet);
        // 如果响应成功,解析响应结果
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 获取响应的内容
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        }
        return null;
    }

    /**
     * 参数的封装
     *
     * @param responseEntityStr 请求的返回值
     * @return 封装后的键值对
     */
    public static Map<String, String> parseResponseEntity(String responseEntityStr) {
        if (responseEntityStr == null) {
            throw new NullPointerException();
        }
        Map<String, String> map = new HashMap<>();
        String[] strs = responseEntityStr.split("&");
        for (String str : strs) {
            String[] mapStrs = str.split("=");
            String value = null;
            String key = mapStrs[0];
            if (mapStrs.length > 1) {
                value = mapStrs[1];
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * json返回值转map
     * @param responseEntityStr 待转换的Json字符串
     * @return 封装后的键值对
     */
    public static Map<String, String> parseResponseEntityJson(String responseEntityStr) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityStr);
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            map.put(key, value);
        }
        return map;
    }
}
