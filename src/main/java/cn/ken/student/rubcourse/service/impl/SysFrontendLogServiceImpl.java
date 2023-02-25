package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.model.dto.sys.req.SysLogPageReq;
import cn.ken.student.rubcourse.model.entity.SysFrontendLog;
import cn.ken.student.rubcourse.mapper.SysFrontendLogMapper;
import cn.ken.student.rubcourse.service.ISysFrontendLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前台日志表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class SysFrontendLogServiceImpl extends ServiceImpl<SysFrontendLogMapper, SysFrontendLog> implements ISysFrontendLogService {

    @Autowired
    private SysFrontendLogMapper sysFrontendLogMapper;

    @Override
    public Result getFrontendLogPage(HttpServletRequest httpServletRequest, SysLogPageReq sysLogPageReq) {
        LambdaQueryWrapper<SysFrontendLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(sysLogPageReq.getUserId() != null, SysFrontendLog::getStudentId, sysLogPageReq.getUserId())
                .eq(sysLogPageReq.getType() != null, SysFrontendLog::getType, sysLogPageReq.getType())
                .ge(sysLogPageReq.getRequestStartTime() != null, SysFrontendLog::getCreateTime, sysLogPageReq.getRequestStartTime())
                .le(sysLogPageReq.getRequestEndTime() != null, SysFrontendLog::getCreateTime, sysLogPageReq.getRequestEndTime());
        Page<SysFrontendLog> page = new Page<>(sysLogPageReq.getPageNo(), sysLogPageReq.getPageSize());
        page = sysFrontendLogMapper.selectPage(page, queryWrapper);
        return Result.success(page);
    }
}
