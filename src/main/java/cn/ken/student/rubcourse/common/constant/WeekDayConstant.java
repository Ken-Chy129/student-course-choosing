package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 22:01
 */
public class WeekDayConstant extends ArrayList<String> {

    public static final WeekDayConstant INSTANCE = new WeekDayConstant();

    private WeekDayConstant() {
        this.add("一");
        this.add("二");
        this.add("三");
        this.add("四");
        this.add("五");
        this.add("六");
        this.add("日");
    }
}
