package cn.ken.student.rubcourse;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/11 17:02
 */
public class MybatisGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/student_course_choosing", "root", "129496")
                .globalConfig(builder -> {
                    builder.author("Ken-Chy129") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\eclipse-workspace\\student-course-choosing\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cn.ken.student") // 设置父包名
                            .moduleName("rubcourse") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\eclipse-workspace\\student-course-choosing\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//                    builder.addInclude("ct_problem", "ct_label", "ct_problem_label"
//                    , "ct_list", "ct_list_problem", "ct_list_type")
                builder
                            .addTablePrefix("scc_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
