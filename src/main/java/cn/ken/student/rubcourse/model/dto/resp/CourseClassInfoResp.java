package cn.ken.student.rubcourse.model.dto.resp;

import cn.ken.student.rubcourse.model.entity.Course;
import cn.ken.student.rubcourse.model.entity.CourseClass;
import cn.ken.student.rubcourse.model.entity.CourseTimeplace;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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
@Builder
public class CourseClassInfoResp implements Serializable {
    
    private Long id;
    
    private String courseId;
    
    private String courseName;
    
    private String campus;
    
    private String placeTime;
    
    private String college;
    
    private String type;
    
    private String generalType;
    
    private Boolean isMooc;
    
    private String language;
    
    private Integer choosingNum;
    
    private Integer capacity;
    
    private BigDecimal credit;
    
    private String examType;

    private String examTime;

    private String teacher;
    
    private Boolean isConflict;
    
    private Boolean isChoose;
    
    @ApiModelProperty(hidden = true)
    private List<CourseTimeplace> courseTimeplaceList;

    public void fillWithCourseClass(CourseClass courseClass) {
        this.setId(courseClass.getId());
        this.setCourseId(courseClass.getCourseId());
        this.setCourseName(courseClass.getCourseName());
        this.setIsMooc(courseClass.getIsMooc());
        this.setLanguage(courseClass.getLanguage());
        this.setChoosingNum(courseClass.getChoosingNum());
        this.setCapacity(courseClass.getCapacity());
        this.setExamType(courseClass.getExamType());
        this.setExamTime(courseClass.getExamTime());
        this.setTeacher(courseClass.getTeacher());
    }

    public void fillWithCourse(Course course) {
        this.setCourseId(course.getId());
        this.setCampus(course.getCampus());
        this.setCourseName(course.getCourseName());
        this.setCollege(course.getCollege());
        this.setType(course.getType());
        this.setGeneralType(course.getGeneralType());
        this.setCredit(course.getCredit());
    }
    
    public void fillWithTimeplace() {
        
    }
}
