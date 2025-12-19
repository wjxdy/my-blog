package com.xulei.myblogbackend.utils;

import java.util.regex.Pattern;

/**
 * 邮箱校验工具类
 */
public class EmailValidator {
    // 通用邮箱正则（覆盖主流邮箱格式，如 xxx@qq.com、xxx@163.com、xxx@company.cn 等）
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(\\.[a-zA-Z]{2,})$"
    );

    /**
     * 校验邮箱格式是否正确
     * @param email 收件人邮箱
     * @return true=格式正确，false=格式错误
     */
    public static boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false; // 空邮箱直接判定无效
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
}