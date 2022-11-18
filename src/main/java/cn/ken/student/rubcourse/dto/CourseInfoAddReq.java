package cn.ken.student.rubcourse.dto;

import cn.ken.student.rubcourse.entity.CourseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/18 22:14
 */
@Data
public class CourseInfoAddReq implements Serializable {

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("所在校区[天河校区,番禺校区,华文校区,珠海校区,深圳校区]")
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

    @ApiModelProperty("课程容量")
    private Integer capacity;

    @ApiModelProperty("课程学分")
    private BigDecimal credit;

    @ApiModelProperty("考试类型")
    private Short examType;

    @ApiModelProperty("教师")
    private String teacher;

    @ApiModelProperty("开课时间")
    private List<String> durationList;

    @ApiModelProperty("开课地点")
    private List<String> placeList;

    @ApiModelProperty("上课时间")
    private List<Short> weekDayList;

    @ApiModelProperty("第几节课上课(1-2,3-4,6-7,6-8,7-8,10-11,10-12)")
    private List<Short> dayNoList;
    
    
}
