package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.annotation.Administrator;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.model.dto.sys.req.SysLogPageReq;
import cn.ken.student.rubcourse.model.entity.SysBackendLog;
import cn.ken.student.rubcourse.mapper.SysBackendLogMapper;
import cn.ken.student.rubcourse.service.ISysBackendLogService;
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
 * 后台日志表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class SysBackendLogServiceImpl extends ServiceImpl<SysBackendLogMapper, SysBackendLog> implements ISysBackendLogService {
    
    @Autowired
    private SysBackendLogMapper sysBackendLogMapper;

    @Override
    @Administrator
    public Result getBackendLogPage(HttpServletRequest httpServletRequest, SysLogPageReq sysLogPageReq) {
        LambdaQueryWrapper<SysBackendLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(sysLogPageReq.getUserId() != null, SysBackendLog::getManagerId, sysLogPageReq.getUserId())
                .eq(sysLogPageReq.getType() != null, SysBackendLog::getType, sysLogPageReq.getType())
                .ge(sysLogPageReq.getRequestStartTime() != null, SysBackendLog::getCreateTime, sysLogPageReq.getRequestStartTime())
                .le(sysLogPageReq.getRequestEndTime() != null, SysBackendLog::getCreateTime, sysLogPageReq.getRequestEndTime());
        Page<SysBackendLog> page = new Page<>(sysLogPageReq.getPageNo(), sysLogPageReq.getPageSize());
        page = sysBackendLogMapper.selectPage(page, queryWrapper);
        return Result.success(page);
    }
}
