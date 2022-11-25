package cn.ken.student.rubcourse.handle;

import cn.ken.student.rubcourse.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/25 17:23
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandle {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result exceptionHandle(Exception e) {
        e.printStackTrace();
        log.error("接口出现内部异常:{}", e.getMessage());
        return Result.fail("接口出现内部异常:" + e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Result throwableHandle(Throwable e) {
        e.printStackTrace();
        log.error("接口出现内部异常:{}", e.getMessage());
        return Result.fail("接口出现内部异常:" + e.getMessage());
    }
}
