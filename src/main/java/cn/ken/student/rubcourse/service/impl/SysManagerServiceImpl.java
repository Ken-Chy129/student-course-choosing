package cn.ken.student.rubcourse.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.ConstantUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.req.ManagerLoginReq;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.entity.SysManager;
import cn.ken.student.rubcourse.mapper.SysManagerMapper;
import cn.ken.student.rubcourse.service.ISysManagerService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系统管理员表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class SysManagerServiceImpl extends ServiceImpl<SysManagerMapper, SysManager> implements ISysManagerService {
    
    @Autowired
    private SysManagerMapper sysManagerMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(HttpServletRequest httpServletRequest, ManagerLoginReq managerLoginReq) {
        LambdaQueryWrapper<SysManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysManager::getManagerName, managerLoginReq.getUsername())
                .eq(SysManager::getIsDeleted, false);
        SysManager sysManager = sysManagerMapper.selectOne(queryWrapper);
        if (sysManager == null) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        String md5Password = DigestUtil.md5Hex(managerLoginReq.getPassword() + sysManager.getSalt());
        if (!md5Password.equals(sysManager.getPassword())) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        Long token = SnowflakeUtil.nextId();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", sysManager.getType().toString());
        redisTemplate.opsForValue().set(RedisConstant.SYSTEM_TOKEN_PREFIX + token.toString(), JSON.toJSONString(hashMap), 86400, TimeUnit.SECONDS);
        SysManager sysManager1 = new SysManager();
        sysManager1.setId(sysManager.getId());
        sysManager1.setLastLogin(LocalDateTime.now());
        sysManagerMapper.updateById(sysManager1);
        hashMap.put("token", token.toString());
        return Result.success(hashMap);
    }
}
