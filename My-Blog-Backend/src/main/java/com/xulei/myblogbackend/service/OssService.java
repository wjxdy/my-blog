package com.xulei.myblogbackend.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * OSS服务接口
 */
public interface OssService {
    
    /**
     * 上传文件到OSS
     *
     * @param file 要上传的文件
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file);
    
    /**
     * 上传文件到OSS，指定目录
     *
     * @param file 要上传的文件
     * @param dir  目标目录
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String dir);
    
    /**
     * 删除OSS上的文件
     *
     * @param fileUrl 文件URL
     * @return 是否删除成功
     */
    boolean deleteFile(String fileUrl);
}
