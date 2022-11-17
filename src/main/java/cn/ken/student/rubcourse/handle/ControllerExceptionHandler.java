package cn.ken.student.rubcourse.handle;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/17 15:17
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * MVC校验参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.debug("[handleMethodArgumentNotValidException]", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append(";");
        }
        String msg = sb.toString();
        log.error("参数异常错误:{}", msg);
        return Result.fail(ErrorCodeEnums.REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnums.REQUEST_PARAM_ERROR.getDesc() + ":" + msg);
    }
    
    /**
     * MVC校验参数异常(get请求)
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result handleMethodBindException(BindException e){
        log.debug("[handleMethodBindException]", e);
        StringBuilder detailMessage = new StringBuilder();
        for (ObjectError objectError : e.getAllErrors()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中
            detailMessage.append(objectError.getDefaultMessage());
        }
        return Result.fail(ErrorCodeEnums.REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnums.REQUEST_PARAM_ERROR.getDesc() + ":" + detailMessage);
    }

    /**
     * JSR-303 参数异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException ex) {
        log.debug("[constraintViolationExceptionHandler]", ex);
        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中
            detailMessage.append(constraintViolation.getMessage());
        }
        // 包装 CommonResult 结果
        return Result.fail(ErrorCodeEnums.REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnums.REQUEST_PARAM_ERROR.getDesc() + ":" + detailMessage);
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.error("接口出现逻辑错误:{}", e.getDesc());
        return Result.fail(e.getCode(), e.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleShiroException(Exception e) {
        e.printStackTrace();
        log.error("接口出现内部异常:{}", e.getMessage());
        return Result.fail("接口出现内部异常:" + e.getMessage());
    }
    
}
