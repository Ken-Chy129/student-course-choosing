package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:11
 */
public class CourseTypeConstant extends ArrayList<String> {
    
    public static final CourseTypeConstant INSTANCE = new CourseTypeConstant();

    private CourseTypeConstant() {
        this.add("通识教育必修课");
        this.add("通识教育选修课");
        this.add("基础教育必修课");
        this.add("基础教育选修课");
        this.add("专业教育必修课");
        this.add("专业教育选修课");
    }
}
