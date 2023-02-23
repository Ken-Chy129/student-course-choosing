package cn.ken.student.rubcourse.common.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.RedisData;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/23 18:33
 */
@Slf4j
@Component
public class CacheClient {

    private final RedisTemplate<String, String> redisTemplate;

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(20), r -> new Thread(r, "cache_rebuild"));

    public CacheClient(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过redis设置过期时间
     */
    public void set(String key, Object value, Long expireTime, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), expireTime, unit);
    }

    /**
     * 逻辑设置过期时间，避免缓存击穿问题
     */
    public void setWithLogicalExpire(String key, Object value, Long expireTime, TimeUnit unit) {
        // 设置逻辑过期时间
        RedisData redisData = new RedisData();
        redisData.setValue(value);
        redisData.setExpireTime(LocalDateTime.now().plusNanos(unit.toNanos(expireTime)));
        redisTemplate.opsForValue().set(key, JSON.toJSONString(redisData));
    }

    /**
     * 获取通过逻辑设置过期时间的缓存
     */
    public <R, V> R get(String keyPrefix, V id, Class<R> clazz, Function<V, R> dbFallback, Long expireTime, TimeUnit unit) {
        String key = keyPrefix + id;
        // 查询缓存
        String value = redisTemplate.opsForValue().get(key);
        // 缓存存在则直接返回
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value, clazz);
        }
        // 缓存不存在(到此处说明value要么是空，要么是null)
        if (value != null) {
            // 如果为空则说明数据不存在，直接返回null，不用查询数据库(解决缓存穿透问题)
            return null;
        }
        // value为null则查询数据库获取数据进行更新
        R result = dbFallback.apply(id);
        if (result == null) {
            // 数据库查询不到结果，则存入空串避免缓存穿透
            redisTemplate.opsForValue().set(key, "", RedisConstant.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        // 查询到结果，写回缓存
        this.set(key, result, expireTime, unit);
        return result;
    }

    public <R, V> R getWithLogicalExpire(String keyPrefix, V id, Class<R> clazz, Function<V, R> dbFallback, Long expireTime, TimeUnit unit) {
        String key = keyPrefix + id;
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        RedisData redisData = JSON.parseObject(value, RedisData.class);
        R result = JSONUtil.toBean((JSONObject) redisData.getValue(), clazz);
        if (redisData.getExpireTime().isAfter(LocalDateTime.now())) {
            return result;
        }
        // 如果缓存已过期，则尝试更新
        String localKey = RedisConstant.LOCK + id;
        // 获取锁成功
        if (getLock(localKey)) {
            // 异步更新缓存
            CACHE_REBUILD_EXECUTOR.submit(
                    () -> {
                        try {
                            R res = dbFallback.apply(id);
                            this.setWithLogicalExpire(key, res, expireTime, unit);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        } finally {
                            unLock(localKey);
                        }
                    }
            );
        }
        return result;
    }

    private boolean getLock(String key) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "1"));
    }

    private void unLock(String key) {
        redisTemplate.delete(key);
    }
}
