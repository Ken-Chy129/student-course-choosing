package cn.ken.student.rubcourse.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * <p>课程信息表(SccCourseInfo)表实体类</p>
 * </pre>
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date  ${DATE} ${TIME}
 */
@Data
@AllArgsConstructor
@TableName("scc_course_class")
@ApiModel(value = "CourseClass对象", description = "课程班信息")
public class CourseClass implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    
    @ApiModelProperty(value = "课程id")
    private String courseId;
    
    @ApiModelProperty(value = "是否慕课")
    private Boolean isMooc;
    
    @ApiModelProperty(value = "授课语言")
    private String language;
    
    @ApiModelProperty(value = "选课人数")
    private Integer choosingNum;
  
    @ApiModelProperty(value = "课程容量")
    private Integer capacity;
    
    @ApiModelProperty(value = "考试类型")
    private String examType;

    @ApiModelProperty("考试时间")
    private String examTime;
    
    @ApiModelProperty(value = "授课教师")
    private String teacher;
    
    @ApiModelProperty(value = "逻辑删除", hidden = true)
    private Integer isDeleted;
    
    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
   
    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

//    @ApiModelProperty(hidden = true)
//    @TableField(exist = false)
//    private Course course;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<CourseTimeplace> courseTimeplaceList;
}

