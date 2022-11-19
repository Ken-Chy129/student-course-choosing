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

    @ApiModelProperty("所在校区[0-天河校区,1-番禺校区,2-华文校区,3-珠海校区,4-深圳校区]")
    private Short campus;

    @ApiModelProperty("考试时间")
    private String examTime;

    @ApiModelProperty("排课单位[1-信息科学技术学院]")
    private Short college;

    @ApiModelProperty("课程类别[0-通识教育必修课,1-通识教育选修课,2-基础教育必修课,3-基础教育选修课,4-通识教育选修课]")
    private Integer type;

    @ApiModelProperty("通识课类别，非通识课则不填[0-经管法类, 1-高级外语类, 2-创新创业类, 3-艺术素养类, 4-数学理工类, 5-生命科学类, 6-文史哲类]")
    private Short generalType;

    @ApiModelProperty("是否慕课")
    private Boolean isMooc;

    @ApiModelProperty("授课语言[0-汉语(中文), 1-中英双语教学, 2-英语, 3-日语, 4-西班牙语, 5-葡萄牙语, 6-德语, 7-法语]")
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

    @ApiModelProperty("第几节课上课(0:1-2,1:3-4,2:6-7,3:6-8,4:7-8,5:10-11,6:10-12)")
    private List<Short> dayNoList;

    @ApiModelProperty("先修课程id")
    private List<String> preCourseIdList;
}
