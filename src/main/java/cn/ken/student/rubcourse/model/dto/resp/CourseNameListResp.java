package cn.ken.student.rubcourse.model.dto.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 23:22
 */
@Data
public class CourseNameListResp implements Serializable {
    
    private String id;
    
    private String courseName;
}
