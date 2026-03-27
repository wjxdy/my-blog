package com.xulei.myblogbackend.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.xulei.myblogbackend.config.properties.AliyunOssProperties;
import com.xulei.myblogbackend.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * OSS服务实现类
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private AliyunOssProperties ossProperties;

    @Override
    public String uploadFile(MultipartFile file) {
        return uploadFile(file, ossProperties.getDirPrefix());
    }

    @Override
    public String uploadFile(MultipartFile file, String dir) {
        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件扩展名
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 生成新的文件名（UUID）
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        // 构建完整路径
        String filePath = dir + newFileName;

        try (InputStream inputStream = file.getInputStream()) {
            // 设置文件元信息
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // 上传文件到OSS
            ossClient.putObject(ossProperties.getBucketName(), filePath, inputStream, metadata);

            // 构建文件访问URL
            String fileUrl = "https://" + ossProperties.getBucketName() + "." 
                    + ossProperties.getEndpoint() + "/" + filePath;
            
            log.info("File uploaded successfully, URL: {}", fileUrl);
            return fileUrl;
        } catch (IOException e) {
            log.error("File upload failed", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            // 从URL中提取文件路径
            String filePath = extractFilePathFromUrl(fileUrl);
            if (filePath == null) {
                return false;
            }
            
            ossClient.deleteObject(ossProperties.getBucketName(), filePath);
            log.info("File deleted successfully: {}", filePath);
            return true;
        } catch (Exception e) {
            log.error("File deletion failed", e);
            return false;
        }
    }
    
    /**
     * 从URL中提取文件路径
     */
    private String extractFilePathFromUrl(String fileUrl) {
        // URL格式: https://bucket.endpoint/dir/filename
        String prefix = "https://" + ossProperties.getBucketName() + "." 
                + ossProperties.getEndpoint() + "/";
        if (fileUrl.startsWith(prefix)) {
            return fileUrl.substring(prefix.length());
        }
        return null;
    }
}
