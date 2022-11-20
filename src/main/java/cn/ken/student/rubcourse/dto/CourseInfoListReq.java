package cn.ken.student.rubcourse.dto;

import cn.ken.student.rubcourse.common.entity.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/18 21:27
 */
@Getter
@Setter
public class CourseInfoListReq extends Page implements Serializable {
    
    @ApiModelProperty("搜索内容(课程编号/课程名称/上课教师)")
    private String searchContent;

    @ApiModelProperty("排课单位")
    private String college;
    
    @ApiModelProperty("通识课类型[0-经管法类, 1-高级外语类, 2-创新创业类, 3-艺术素养类, 4-数学理工类, 5-生命科学类, 6-文史哲类]")
    private String generalType;

    @ApiModelProperty("学分")
    private BigDecimal credit;

    @ApiModelProperty("所在校区[天河校区,番禺校区,华文校区,珠海校区,深圳校区]")
    private String campus;

    @ApiModelProperty("是否慕课")
    private Boolean isMooc;

    @ApiModelProperty("上课时间")
    private Integer weekDay;
    
}
