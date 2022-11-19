package cn.ken.student.rubcourse.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/20 0:08
 */
public class ConstantUtil {

    public static HashMap<String, String> getHashMap(ArrayList<String> arrayList) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i=0; i<arrayList.size(); i++) {
            hashMap.put(String.valueOf(i), arrayList.get(i));
        }
        return hashMap;
    }
}
