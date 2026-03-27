package com.xulei.myblogbackend.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xulei.myblogbackend.config.properties.AliyunOssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置类
 */
@Slf4j
@Configuration
public class OssConfig {

    @Autowired
    private AliyunOssProperties ossProperties;

    /**
     * 创建OSS客户端
     */
    @Bean
    public OSS ossClient() {
        log.info("Initializing OSS client, endpoint: {}", ossProperties.getEndpoint());
        return new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret()
        );
    }
}
