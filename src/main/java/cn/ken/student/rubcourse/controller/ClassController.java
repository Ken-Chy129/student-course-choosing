package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/class")
public class ClassController {
    
    @Autowired
    private IClassService classService;
    

}
