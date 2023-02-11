package cn.ken.student.rubcourse.common.constant;

import java.util.ArrayList;

/**
 * <pre>
 * 下拉框常量类
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/11 16:12
 */
public class ComboBoxConstant {

    public static class CampusConstant extends ArrayList<String> {
        public static final CampusConstant INSTANCE = new CampusConstant();

        private CampusConstant() {
            this.add("天河校区");
            this.add("番禺校区");
            this.add("华文校区");
            this.add("珠海校区");
            this.add("深圳校区");
        }
    }

    public static class CourseTypeConstant extends ArrayList<String> {

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

    public static class DayNoConstant extends ArrayList<String> {

        public static final DayNoConstant INSTANCE = new DayNoConstant();

        private DayNoConstant() {
            this.add("第1节-第2节");
            this.add("第3节-第4节");
            this.add("第6节-第7节");
            this.add("第6节-第8节");
            this.add("第7节-第8节");
            this.add("第10节-第11节");
            this.add("第10节-第12节");
        }
    }

    public static class ExamTypeConstant extends ArrayList<String> {

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

    public static class GeneralTypeConstant extends ArrayList<String> {

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

    public static class LanguageTypeConstant extends ArrayList<String> {

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

    public static class WeekDayConstant extends ArrayList<String> {

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

}
