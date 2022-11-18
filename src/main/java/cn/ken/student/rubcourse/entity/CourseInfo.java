package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程信息表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_course_info")
@ApiModel(value = "CourseInfo对象", description = "课程信息表")
public class CourseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程编号")
    private String id;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("所在校区")
    private Short campus;

    @ApiModelProperty("考试时间")
    private String examTime;

    @ApiModelProperty("排课单位")
    private Short collegeId;

    @ApiModelProperty("课程类别[基础必修,专业必修,基础选修,专业选修,通识选修]")
    private Short type;

    @ApiModelProperty("通识课类别")
    private Short generalType;

    @ApiModelProperty("是否慕课")
    private Boolean isMooc;

    @ApiModelProperty("授课语言")
    private Short language;

    @ApiModelProperty("已选人数")
    private Integer choosingNum;

    @ApiModelProperty("课程容量")
    private Integer capacity;

    @ApiModelProperty("课程学分")
    private BigDecimal credit;

    @ApiModelProperty("考试类型")
    private Short examType;

    @ApiModelProperty("教师")
    private String teacher;

    @ApiModelProperty("课程状态")
    private Short status;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
