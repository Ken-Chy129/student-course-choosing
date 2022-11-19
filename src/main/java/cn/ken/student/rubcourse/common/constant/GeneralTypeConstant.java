package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:21
 */
public class GeneralTypeConstant extends ArrayList<String> {

    public static final GeneralTypeConstant INSTANCE = new GeneralTypeConstant();

    private GeneralTypeConstant() {
        this.add("经管法类");
        this.add("高级外语类");
        this.add("创新创业类");
        this.add("艺术素养类");
        this.add("数学理工类");
        this.add("生命科学类");
        this.add("文史哲类");
    }
}
