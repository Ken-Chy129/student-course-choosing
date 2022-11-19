package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:02
 */
public class CampusConstant extends ArrayList<String> {
    
    public static final CampusConstant INSTANCE = new CampusConstant();
    
    private CampusConstant() {
        this.add("天河校区");
        this.add("番禺校区");
        this.add("华文校区");
        this.add("珠海校区");
        this.add("深圳校区");
    }
}
