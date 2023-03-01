package cn.ken.student.rubcourse.config;

import cn.ken.student.rubcourse.listener.KeyExpiredListener;
import cn.ken.student.rubcourse.model.entity.Student;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.math.BigDecimal;

@Configuration
public class RedisConfig {
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }

    @Bean
    public KeyExpiredListener keyExpiredListener() {
        return new KeyExpiredListener(this.redisMessageListenerContainer());
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        // 1.创建redisTemplate模板
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        // 2.关联redisConnectionFactory
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 3.创建序列化类
        /*
        Jackson2JsonRedisSerializer序列化方式：
        1.如果VALUE直接存对象或者对象集合，获取时结果为LinkedHashMap,无法直接使用JDK强转方法转为对象或对象集合，需借助ObjectMapper实现对象直接序列化存储到Redis,从Redis读取反序列化对象
        2.如果VALUE存JSON字符串，取值也为JSON字符串,需借助JSON工具进行转换为实体类或实体集合
         */
        Jackson2JsonRedisSerializer<String> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 4.设置可见度
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 5.启动默认的类型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 6.序列化类，对象映射设置
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 7.设置value的转化格式和key的转换格式
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
	
}
