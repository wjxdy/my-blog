package com.xulei.myblogbackend.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.dto.EmailVerifyDto;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.entity.User;
import com.xulei.myblogbackend.service.EmailService;
import com.xulei.myblogbackend.service.UserService;
import com.xulei.myblogbackend.utils.EmailValidator;
import com.xulei.myblogbackend.utils.SendEmailUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import static com.xulei.myblogbackend.utils.StringContext.*;


@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private SendEmailUtil sendEmailUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    
    @Override
    public void getEmailCode(EmailVerifyDto emailVerifyDto) throws BaseException {
        String email = emailVerifyDto.getEmailAddress();
        String inputCode = emailVerifyDto.getCode();
        // 1. 校验入参非空
        if (email == null || email.isBlank() || inputCode == null || inputCode.isBlank()) {
            throw new BaseException("邮箱或验证码不能为空");
        }
        String redisKey = REDIS_EMAIL_SEND_KEY + email;
        // 2. 从Redis获取值（避免空指针：先判断是否存在，再获取）
        Boolean hasKey = stringRedisTemplate.hasKey(redisKey);
        if (hasKey) {
            throw new BaseException("验证码已过期或未发送，请重新获取");
        }
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        // 3. 若已是已验证状态（"1"），直接返回成功
        if (EMAIL_VERIFIED_FLAG.equals(redisValue)) {
            throw new BaseException("该邮箱已验证，请尽快完成注册");
        }
        // 4. 对比验证码
        if (!redisValue.equals(inputCode)) {
            throw new BaseException("验证码错误");
        }
        // 4. 邮箱唯一性校验（补充必要校验：避免同一邮箱注册多个账号）
        boolean emailExists = userService.exists(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email));
        if (emailExists) {
            throw new BaseException("该邮箱已注册账号，请直接登录");
        }
        // 5. 验证通过：标记为已验证（"1"），续期10分钟
        stringRedisTemplate.opsForValue().set(redisKey, EMAIL_VERIFIED_FLAG, EMAIL_VERIFIED_EXPIRE, TimeUnit.SECONDS);
        log.info("校验通过{}",email);
    }
    

    @Override
    public void sendMessage(String emailAddress) throws BaseException {
        if (!EmailValidator.isEmailValid(emailAddress)) {
            throw new BaseException("邮件格式错误");
        }
        String key = REDIS_EMAIL_SEND_KEY+emailAddress;

        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (hasKey) {
            String redisValue = stringRedisTemplate.opsForValue().get(key);
            // 区分状态：已验证则提示注册，未验证则提示勿重复发送
            if (EMAIL_VERIFIED_FLAG.equals(redisValue)) {
                throw new BaseException("该邮箱已验证，请直接完成注册");
            } else {
                throw new BaseException("验证码已发送，1分钟内有效，请勿重复请求");
            }
        }
        // 3. 生成6位验证码（ThreadLocalRandom更安全高效）
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
        // 脱敏日志，避免泄露
        log.info("向邮箱[{}]发送注册验证码：****{}", emailAddress, code.substring(2));
        try {
            sendEmailUtil.sendMessage(emailAddress,code);
            stringRedisTemplate.opsForValue().set(key, code,PREFIX_EMAIL_VERIFIED_EXPIRE, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("{}",e.getMessage());
            throw new BaseException("邮件发送失败!请重试！");
        }
        
    }
}
