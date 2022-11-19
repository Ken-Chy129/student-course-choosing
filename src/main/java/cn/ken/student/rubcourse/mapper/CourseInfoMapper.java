package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.dto.CourseNameListResp;
import cn.ken.student.rubcourse.entity.CourseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程信息表 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface CourseInfoMapper extends BaseMapper<CourseInfo> {
    
    List<CourseInfo> getCourseInfoPage(CourseInfoListReq courseInfoListReq);
    
    List<CourseNameListResp> getCourseNameList(String searchContent);
}
