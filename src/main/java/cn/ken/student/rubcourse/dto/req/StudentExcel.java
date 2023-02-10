package cn.ken.student.rubcourse.dto.req;

import cn.ken.student.rubcourse.entity.Student;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/12/13 23:57
 */
@Data
public class StudentExcel implements Serializable {

    @ExcelProperty("学生学号")
    private Long id;

    @ExcelProperty("学生姓名")
    private String name;

    @ExcelProperty("学生性别(0-女,1-男)")
    private Integer gender;

    @ExcelProperty("班级编号")
    private Integer classId;

    @ExcelProperty("学生状态(0-正常,1-毕业,2-其他)")
    private Integer status;

    @ExcelProperty("学生邮箱")
    private String email;

    @ExcelProperty("学生电话")
    private String phone;

    @ExcelProperty("学生身份证")
    private String idCard;

    @ExcelProperty("学生密码")
    private String password;
    
    public void fillStudent(Student student) {
        student.setId(id);
        student.setName(name);
        student.setGender(gender);
        student.setClassId(classId);
        student.setStatus(status);
        student.setEmail(email);
        student.setPhone(phone);
        student.setIdCard(idCard);
        student.setPassword(password);
    }
}
