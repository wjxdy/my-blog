//package com.xulei.myblogbackend;
//
//import com.xulei.myblogbackend.utils.EmailTemplateUtil;
//import jakarta.mail.internet.MimeMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@SpringBootTest
//class MyBlogBackendApplicationTests {
//
//
//    @Autowired
//    JavaMailSender javaMailSender;
//    @Value("${spring.mail.from.username}")
//    private String fromEmall;
//    @Value("${spring.mail.from.name}")
//    private String fromName;
//    @Test
//    public void test() throws Exception {
//
//        // 创建一个邮件消息
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        // 创建 MimeMessageHelper
//        MimeMessageHelper helper = new MimeMessageHelper(message, false);
//
//        // 发件人邮箱和名称
//        helper.setFrom(fromEmall, fromName);
//        // 收件人邮箱
//        helper.setTo("markyhashia@gmail.com");
//        // 邮件标题
//        helper.setSubject("Hello");
//
//        // 3. 读取HTML模板并替换验证码
//        String templatePath = "templates/email/verification-code.html"; // 模板路径（resources下）
//        Map<String, String> variables = new HashMap<>();
//        variables.put("code", "11223344"); // 替换 {{code}} 为实际验证码
//        String emailHtmlContent = EmailTemplateUtil.getTemplateContent(templatePath, variables);
//
//        // 邮件正文，第二个参数表示是否是HTML正文
//        helper.setText(emailHtmlContent, true);
//
//        // 发送
//        try {
//            javaMailSender.send(message);
//
//            log.info("邮件发送成功");
//        } catch (MailException e) {
//            log.error("邮件发送失败");
//            throw new RuntimeException(e);
//
//        }
//    }
//
//
//}
