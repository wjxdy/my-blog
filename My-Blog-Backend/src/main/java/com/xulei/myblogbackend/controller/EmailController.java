package com.xulei.myblogbackend.controller;
import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.dto.EmailVerifyDto;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.service.EmailService;
import com.xulei.myblogbackend.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author xulei
 */
@Slf4j
@RequestMapping("/email")
@RestController
public class EmailController  {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EmailService emailService;

    @PostMapping("/verifyCode")
    public Result getEmailCode(@RequestBody EmailVerifyDto emailVerifyDto){
        try {
            emailService.getEmailCode(emailVerifyDto);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok("验证码验证通过，请尽快完成注册");
    }


    @GetMapping("/sendMessage/{emailAddress}")
    public Result sendMessage(@PathVariable("emailAddress") String emailAddress ) {
        try {
            emailService.sendMessage(emailAddress);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok("邮件发送成功");
    }


}
