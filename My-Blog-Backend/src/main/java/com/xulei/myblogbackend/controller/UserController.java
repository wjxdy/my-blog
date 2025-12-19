package com.xulei.myblogbackend.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.dto.RegisterDto;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.entity.User;
import com.xulei.myblogbackend.service.UserService;
import com.xulei.myblogbackend.utils.JwtUtils;
import com.xulei.myblogbackend.vo.UserLoginInfoVo;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.xulei.myblogbackend.utils.StringContext.EMAIL_VERIFIED_FLAG;
import static com.xulei.myblogbackend.utils.StringContext.REDIS_EMAIL_SEND_KEY;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto){
        try {
            userService.register(registerDto);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok("注册成功");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user ){
        UserLoginInfoVo userLoginInfo = new UserLoginInfoVo();
        try {
            userLoginInfo = userService.login(user);
        } catch (BaseException e) {
            Result.fail(e.getMessage());
        }
        return Result.success(userLoginInfo);
    }


    @PostMapping("/")
    public Result add(@RequestBody User user){
        if (userService.save(user)){
            return Result.ok();
        }
        return Result.fail("添加失败");
    }


}