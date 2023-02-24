package cn.ken.student.rubcourse.model.dto.sys.resp;

import cn.ken.student.rubcourse.model.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/29 11:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResp implements Serializable {

    private Long id;
    
    private String name;

    private Integer gender;

    private String className;

    private Integer status;

    private String email;

    private String phone;

    private String idCard;

    private String password;

    private String salt;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    
    public StudentResp(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.className = student.getName();
        this.gender = student.getGender();
        this.status = student.getStatus();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.idCard = student.getIdCard();
        this.password = student.getPassword();
        this.salt = student.getSalt();
        this.createTime = student.getCreateTime();
        this.updateTime = student.getUpdateTime();
    }
    
}
