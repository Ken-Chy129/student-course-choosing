package cn.ken.student.rubcourse.dto;

import cn.ken.student.rubcourse.entity.CourseInfo;
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
 * @date 2022/11/19 21:09
 */
@Data
public class CourseDetailResp implements Serializable {
    
    private String id;
    
    private String courseName;
    
    private String campus;
    
    private String placeTime;
    
    private String examTime;
    
    private String college;
    
    private String type;
    
    private String generalType;
    
    private Boolean isMooc;
    
    private String language;
    
    private Integer chooseNum;
    
    private Integer capacity;
    
    private BigDecimal credit;
    
    private String examType;
    
    private String teacher;
    
    private Boolean isConflict;
    
    public CourseDetailResp() {}
    
    public CourseDetailResp(CourseInfo courseInfo) {
        this.id = courseInfo.getId();
        this.courseName = courseInfo.getCourseName();
        this.campus = courseInfo.getCampus();
        this.examTime = courseInfo.getExamTime();
        this.college = courseInfo.getCollege();
        this.type = courseInfo.getType();
        this.generalType = courseInfo.getGeneralType();
        this.isMooc = courseInfo.getIsMooc();
        this.language = courseInfo.getLanguage();
        this.chooseNum = courseInfo.getChoosingNum();
        this.capacity = courseInfo.getCapacity();
        this.credit = courseInfo.getCredit();
        this.examType = courseInfo.getExamType();
        this.teacher = courseInfo.getTeacher();
    }
    
}
