package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:26
 */
public class LanguageTypeConstant extends ArrayList<String> {

    public static final LanguageTypeConstant INSTANCE = new LanguageTypeConstant();

    private LanguageTypeConstant() {
        this.add("汉语(中文)");
        this.add("中英双语教学");
        this.add("英语");
        this.add("日语");
        this.add("西班牙语");
        this.add("葡萄牙语");
        this.add("德语");
        this.add("法语");
    }
    
}
