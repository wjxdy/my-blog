package com.xulei.myblogbackend.utils;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring Boot 官方 JavaMailSender 实现（无任何冲突，支持文本/附件/HTML）
 */

@Slf4j
@Component
public class SendEmailUtil {


    private final JavaMailSender javaMailSender;

    @Value("${mail.from.address}")
    private String fromEmail = "2077152374@qq.com";

    @Value("${mail.from.name}")
    private String fromName = "小朱笔记";

    // 新增构造方法：接收JavaMailSender + 发件人邮箱 + 发件人名称
    public SendEmailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }



    //邮件发送工具
    public void sendMessage(String emailAddress,String code) throws Exception {
        try {
            // 创建一个邮件消息
            MimeMessage message = javaMailSender.createMimeMessage();
            // 创建 MimeMessageHelper
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            // 发件人邮箱和名称
            helper.setFrom(fromEmail, fromName);
            // 收件人邮箱
            helper.setTo(emailAddress);
            // 邮件标题
            helper.setSubject("小朱笔记");
            // 3. 读取HTML模板并替换验证码
            String templatePath = "templates/email/verification-code.html"; // 模板路径（resources下）
            Map<String, String> variables = new HashMap<>();
            variables.put("code", code); // 替换 {{code}} 为实际验证码
            String emailHtmlContent = EmailTemplateUtil.getTemplateContent(templatePath, variables);
            // 邮件正文，第二个参数表示是否是HTML正文
            helper.setText(emailHtmlContent, true);
            // 发送

            javaMailSender.send(message);
            log.info("邮件发送成功");
        } catch (MailException e) {
            log.error("邮件发送失败");
            throw new RuntimeException(e);
        }
    }




}