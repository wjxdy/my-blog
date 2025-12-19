package com.xulei.myblogbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 1. 配置 ObjectMapper（处理序列化细节，如时间类型、子类类型）
        ObjectMapper objectMapper = new ObjectMapper();
        // 支持 JSR310 时间类型（LocalDateTime、LocalDate 等）
        objectMapper.registerModule(new JavaTimeModule());
        // 开启字段可见性配置（允许序列化/反序列化所有字段，包括 private）
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 启用子类类型识别（序列化时保留对象类型信息，反序列化时能正确转换为原对象）
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL // 非 final 类才启用类型识别
        );

        // 2. 关键修复：通过构造函数传入 ObjectMapper，替代 setObjectMapper()
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
        // （旧写法：jacksonSerializer.setObjectMapper(objectMapper); → 已过时，删除此行）

        // 3. String 序列化器（处理 Key 和 HashKey）
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 4. 配置 RedisTemplate 的序列化方式
        template.setKeySerializer(stringSerializer); // Key 用 String 序列化
        template.setHashKeySerializer(stringSerializer); // HashKey 用 String 序列化
        template.setValueSerializer(jacksonSerializer); // Value 用 Jackson 序列化
        template.setHashValueSerializer(jacksonSerializer); // HashValue 用 Jackson 序列化

        // 初始化模板
        template.afterPropertiesSet();
        return template;
    }

    // StringRedisTemplate 无需修改（Spring Boot 自动配置，默认 String 序列化）
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
}