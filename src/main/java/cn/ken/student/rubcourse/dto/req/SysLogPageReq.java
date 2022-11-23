package cn.ken.student.rubcourse.dto.req;

import cn.ken.student.rubcourse.common.entity.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/23 20:03
 */
@Getter
@Setter
public class SysLogPageReq extends Page implements Serializable {
    
    private Long userId;
    
    @ApiModelProperty("请求类型,0-正常,1-异常")
    private Boolean type;
    
    @ApiModelProperty("请求起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestStartTime;

    @ApiModelProperty("请求终止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestEndTime;
}
