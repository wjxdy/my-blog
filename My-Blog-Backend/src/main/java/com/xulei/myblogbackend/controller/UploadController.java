package com.xulei.myblogbackend.controller;

import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private OssService ossService;

    /**
     * 上传图片接口
     *
     * @param file 图片文件
     * @return 图片访问URL
     */
    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("请选择要上传的文件");
        }
        
        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return Result.fail("只允许上传图片文件");
        }
        
        // 验证文件大小（5MB）
        if (file.getSize() > 5 * 1024 * 1024) {
            return Result.fail("文件大小不能超过5MB");
        }
        
        try {
            String fileUrl = ossService.uploadFile(file, "images/");
            return Result.success(fileUrl);
        } catch (Exception e) {
            log.error("图片上传失败", e);
            return Result.fail("图片上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 上传头像接口（专用于用户头像上传）
     *
     * @param file 头像图片
     * @return 头像访问URL
     */
    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("请选择要上传的头像");
        }
        
        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return Result.fail("只允许上传图片文件");
        }
        
        // 验证文件大小（2MB）
        if (file.getSize() > 2 * 1024 * 1024) {
            return Result.fail("头像文件大小不能超过2MB");
        }
        
        try {
            String fileUrl = ossService.uploadFile(file, "avatars/");
            return Result.success(fileUrl);
        } catch (Exception e) {
            log.error("头像上传失败", e);
            return Result.fail("头像上传失败: " + e.getMessage());
        }
    }
}
