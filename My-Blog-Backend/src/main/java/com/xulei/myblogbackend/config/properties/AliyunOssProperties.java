package com.xulei.myblogbackend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {
    
    /**
     * OSS服务的Endpoint
     */
    private String endpoint;
    
    /**
     * 访问密钥ID
     */
    private String accessKeyId;
    
    /**
     * 访问密钥Secret
     */
    private String accessKeySecret;
    
    /**
     * 存储空间名称
     */
    private String bucketName;
    
    /**
     * 上传文件的前缀路径
     */
    private String dirPrefix;
}
