package cn.ken.student.rubcourse.common.entity;

import cn.hutool.core.lang.UUID;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 简易实现的Redis分布式锁
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/26 21:18
 */
public class SimpleRedisLock {

    private final RedisTemplate<String, String> redisTemplate;
    
    /**
    锁的名字，根据业务设置
     */
    private final String lockName;

    /**
     * key前缀
     */
    private static final String KEY_PREFIX = "lock:";

    /**
     * value中线程标识的前缀（为每个节点提供一个随机的前缀，避免集群部署下线程id出现重复而导致value出现相同的情况）
     */
    private static final String ID_PREFIX = UUID.fastUUID().toString(true);

    /**
     * 释放锁逻辑的lua脚本
     */
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    public SimpleRedisLock(String lockName, RedisTemplate<String, String> redisTemplate) {
        this.lockName = lockName;
        this.redisTemplate = redisTemplate;
    }

    public boolean tryLock(long timeoutSec) {
        long threadId = Thread.currentThread().getId();
        // 返回的是Boolean类型，直接return会进行自动拆箱，可能会出现空指针异常
        // 需要为锁设置过期时间，防止因服务宕机而导致锁无法释放
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + lockName, ID_PREFIX + threadId, timeoutSec, TimeUnit.SECONDS));
    }

    public void unlock() {
        redisTemplate.execute(
                UNLOCK_SCRIPT,
                Collections.singletonList(KEY_PREFIX + lockName),
                ID_PREFIX + Thread.currentThread().getId()
        );
    }
}
