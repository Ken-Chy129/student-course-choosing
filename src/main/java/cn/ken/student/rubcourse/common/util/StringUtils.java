package cn.ken.student.rubcourse.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/16 23:12
 */
public class StringUtils {

    public static String toString(Object object) {
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public static String nameDesensitization(String name){
        if(name==null || name.isEmpty()){
            return "";
        }
        String myName = null;
        char[] chars = name.toCharArray();
        if(chars.length==1){
            myName=name;
        }
        if(chars.length==2){
            myName=name.replaceFirst(name.substring(1), "*");
        }
        if(chars.length>2){
            myName=name.replaceAll(name.substring(1, chars.length-1), "*");
        }
        return myName;
    }

    public static String custNoDesensitization(String custNo){
        if(custNo==null || custNo.isEmpty()){
            return "";
        }
        char[] chars = custNo.toCharArray();
        for(int i=0 ;i<chars.length;i++){
            if(i>5 && i< chars.length-4){
                chars[i]='*';
            }
        }
        return String.valueOf(chars);
    }

    public static String phoneDesensitization(String phoneNumber){
        char[] chars = phoneNumber.toCharArray();
        for(int i=0; i<chars.length; i++){
            if(i>=3 && i<chars.length-4){
                chars[i]='*';
            }
        }
        return String.valueOf(chars);
    }
}
