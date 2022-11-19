package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:23
 */
public class ExamTypeConstant extends ArrayList<String> {

    public static final ExamTypeConstant INSTANCE = new ExamTypeConstant();

    private ExamTypeConstant() {
        this.add("笔试");
        this.add("开卷半开卷");
        this.add("开卷考试");
        this.add("论文/写作");
        this.add("实际操作");
        this.add("口试");
    }
}
