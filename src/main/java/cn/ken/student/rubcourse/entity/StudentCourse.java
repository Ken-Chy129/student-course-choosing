package cn.ken.student.rubcourse.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.bigdecimal.BigDecimalStringConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanNumberConverter;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 学生选课表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_student_course")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "StudentCourse对象", description = "学生选课表")
public class StudentCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    private Long id;

    @ApiModelProperty("学生学号")
    @ExcelProperty(converter = LongStringConverter.class)
    private Long studentId;

    @ApiModelProperty("课程班编号")
    @ExcelProperty(converter = LongStringConverter.class)
    private Long courseClassId;

    @ApiModelProperty("选择学期")
    @ExcelProperty(converter = IntegerStringConverter.class)
    private Integer semester;

    @ApiModelProperty("课程学分")
    @ExcelProperty(converter = BigDecimalStringConverter.class)
    private BigDecimal credits;

    @ApiModelProperty("逻辑删除")
    @ExcelProperty(converter = BooleanNumberConverter.class)
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    @ExcelProperty(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime updateTime;

}
