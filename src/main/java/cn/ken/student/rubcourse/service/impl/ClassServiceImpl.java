package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.mapper.ClassMapper;
import cn.ken.student.rubcourse.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

}
