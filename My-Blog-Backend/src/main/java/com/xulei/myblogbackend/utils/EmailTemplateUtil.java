package com.xulei.myblogbackend.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮件模板工具类：读取HTML模板 + 替换动态变量
 */
public class EmailTemplateUtil {

    // 匹配占位符：{{变量名}}（如 {{code}}）
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(\\w+)\\}\\}");

    /**
     * 读取HTML模板并替换变量
     * @param templatePath 模板在resources下的路径（如 "templates/email/verification-code.html"）
     * @param variables 动态变量映射（key：占位符名称，value：实际值）
     * @return 替换后的HTML字符串
     * @throws IOException 读取模板文件失败时抛出
     */
    public static String getTemplateContent(String templatePath, Map<String, String> variables) throws IOException {
        // 1. 读取resources下的HTML模板文件
        Resource resource = new ClassPathResource(templatePath);
        byte[] contentBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String templateContent = new String(contentBytes, StandardCharsets.UTF_8);

        // 2. 替换所有占位符（{{变量名}}）
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(templateContent);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String placeholderKey = matcher.group(1); // 获取占位符名称（如 "code"）
            // 从变量映射中获取对应值，若不存在则保留原占位符
            String replacement = variables.getOrDefault(placeholderKey, matcher.group(0));
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
