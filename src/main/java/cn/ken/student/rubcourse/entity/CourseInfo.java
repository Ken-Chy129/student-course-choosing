package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
    private Integer id;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("所在校区(0-天河校区,1-番禺校区.2-华文校区,3-珠海校区,4-深圳校区)")
    private Integer campus;

    @ApiModelProperty("考试时间")
    private String examTime;

    @ApiModelProperty("排课单位")
    private String departmentName;

    @ApiModelProperty("课组名称")
    private String groupName;

    @ApiModelProperty("课程类别")
    private Integer type;

    @ApiModelProperty("通识课类别")
    private Integer generalType;

    @ApiModelProperty("是否慕课")
    private Boolean isMooc;

    @ApiModelProperty("授课语言")
    private Integer language;

    @ApiModelProperty("已选人数")
    private Integer choosingNum;

    @ApiModelProperty("课程容量")
    private Integer capacity;

    @ApiModelProperty("课程学分")
    private Integer credit;

    @ApiModelProperty("考试类型")
    private Integer examType;

    @ApiModelProperty("所属学期")
    private Integer semester;

    @ApiModelProperty("课程状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
