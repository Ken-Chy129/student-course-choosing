package cn.ken.student.rubcourse.dto.sys.req;

import cn.ken.student.rubcourse.common.entity.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/23 14:18
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysNoticePageReq extends Page implements Serializable {
    
    @ApiModelProperty("-1表示公告,不填写则查询所有")
    private Long studentId;
}
