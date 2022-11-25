package cn.ken.student.rubcourse.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ken.student.rubcourse.annotation.Administrator;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.req.ManagerLoginReq;
import cn.ken.student.rubcourse.entity.SysManager;
import cn.ken.student.rubcourse.mapper.SysManagerMapper;
import cn.ken.student.rubcourse.service.ISysManagerService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
        System.out.println(sysManager);
        if (sysManager == null) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        String md5Password = DigestUtil.md5Hex(managerLoginReq.getPassword() + sysManager.getSalt());
        System.out.println(md5Password);
        if (!md5Password.equals(sysManager.getPassword())) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        Long token = SnowflakeUtil.nextId();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", sysManager.getType().toString());
        hashMap.put("id", String.valueOf(sysManager.getId()));
        redisTemplate.opsForValue().set(RedisConstant.SYSTEM_TOKEN_PREFIX + token.toString(), JSON.toJSONString(hashMap), 86400, TimeUnit.SECONDS);
        SysManager sysManager1 = new SysManager();
        sysManager1.setId(sysManager.getId());
        sysManager1.setLastLogin(LocalDateTime.now());
        sysManagerMapper.updateById(sysManager1);
        hashMap.put("token", token.toString());
        return Result.success(hashMap);
    }

    @Override
    public Result logout(HttpServletRequest httpServletRequest, Long token) {
        redisTemplate.delete(RedisConstant.SYSTEM_TOKEN_PREFIX + token.toString());
        return Result.success();
    }

    @Override
    @Administrator
    public Result getManagerList(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.SYSTEM_TOKEN_PREFIX + token), HashMap.class);
        if (hashMap == null) {
            return Result.fail(ErrorCodeEnums.SYS_UN_LOGIN);
        }
        String type = hashMap.get("type");
        if (type.equals("0")) {
            return Result.fail(ErrorCodeEnums.SYS_NO_PERMISSION);
        }
        LambdaQueryWrapper<SysManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysManager::getIsDeleted, false);
        return Result.success(sysManagerMapper.selectList(queryWrapper));
    }

    @Override
    @Administrator
    public Result addManager(HttpServletRequest httpServletRequest, SysManager sysManager) {
        String salt = IdUtil.simpleUUID();
        String md5Password = DigestUtil.md5Hex(sysManager.getPassword() + salt);
        sysManager.setPassword(md5Password);
        sysManager.setSalt(salt);
        sysManagerMapper.insert(sysManager);
        return Result.success();
    }

    @Override
    @Administrator
    public Result updateManager(HttpServletRequest httpServletRequest, SysManager sysManager) {
        SysManager sysManager1 = sysManagerMapper.selectById(sysManager.getId());
        String salt = sysManager1.getSalt();
        LambdaUpdateWrapper<SysManager> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysManager::getId, sysManager.getId())
                        .set(sysManager.getIsDeleted() != null, SysManager::getIsDeleted, sysManager.getIsDeleted())
                        .set(sysManager.getType() != null, SysManager::getType, sysManager.getType())
                        .set(StringUtils.isNotBlank(sysManager.getPassword()), SysManager::getPassword, DigestUtil.md5Hex(sysManager.getPassword() + salt))
                        .set(StringUtils.isNotBlank(sysManager.getManagerName()), SysManager::getManagerName, sysManager.getManagerName())
                        .set(StringUtils.isNotBlank(sysManager.getMobilePhone()), SysManager::getMobilePhone, sysManager.getMobilePhone());
        sysManagerMapper.update(null, updateWrapper);
        return Result.success();
    }
}
