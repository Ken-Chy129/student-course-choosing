package cn.ken.student.rubcourse.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/17 23:04
 */
@Slf4j
@RequiredArgsConstructor
public class FillMetaObjectHandle implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.findProperty("createTime", true) != null) {
            // 创建时间
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        if (metaObject.findProperty("updateTime", true) != null) {
            // 创建时间
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.findProperty("updateTime", true) != null) {
            // 创建时间
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
