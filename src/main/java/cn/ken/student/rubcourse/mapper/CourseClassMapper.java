package cn.ken.student.rubcourse.mapper;

import java.util.List;

import cn.ken.student.rubcourse.dto.resp.AllCourseListResp;
import cn.ken.student.rubcourse.dto.req.AllCourseListReq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.ken.student.rubcourse.entity.CourseClass;

/**
 * <pre>
 * <p>课程信息表(SccCourseInfo)表数据库访问层</p>
 * </pre>
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date  ${DATE} ${TIME}
 */
@Mapper
public interface CourseClassMapper extends BaseMapper<CourseClass> {

    List<AllCourseListResp> getCourseInfoPage(AllCourseListReq allCourseListReq);
    
}

