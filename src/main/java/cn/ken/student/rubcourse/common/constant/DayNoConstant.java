package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 15:31
 */
public class DayNoConstant extends ArrayList<String> {

    public static final DayNoConstant INSTANCE = new DayNoConstant();

    private DayNoConstant() {
        this.add("第1节-第2节");
        this.add("第3节-第4节");
        this.add("第6节-第7节");
        this.add("第6节-第8节");
        this.add("第7节-第8节");
        this.add("第10节-第11节");
        this.add("第11节-第12节");
    }
}
