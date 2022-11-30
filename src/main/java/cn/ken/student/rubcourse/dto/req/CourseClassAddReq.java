package cn.ken.student.rubcourse.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class CourseClassAddReq implements Serializable {
    
    @ApiModelProperty("课程id")
    private String courseId;

    @ApiModelProperty("考试时间")
    private String examTime;

    @ApiModelProperty("是否慕课")
    private Boolean isMooc;

    @ApiModelProperty("授课语言[0-汉语(中文), 1-中英双语教学, 2-英语, 3-日语, 4-西班牙语, 5-葡萄牙语, 6-德语, 7-法语]")
    private Short language;

    @ApiModelProperty("课程容量")
    private Integer capacity;
    
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

    @ApiModelProperty("第几节课上课(0:1-2,1:3-4,2:6-7,3:6-8,4:7-8,5:10-11,6:10-12)")
    private List<Short> dayNoList;

}
