package com.xulei.myblogbackend.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.entity.User;
import com.xulei.myblogbackend.service.UserService;
import com.xulei.myblogbackend.utils.JwtUtils;
import com.xulei.myblogbackend.vo.UserLoginInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")

public class UserController {

    private final UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody User user ){

        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername())
                        .eq(User::getPassword,user.getPassword());
        boolean exists = userService.exists(queryWrapper);


        if(!exists){


            return Result.fail("账号或密码错误");

        }
        //创建返回给前端的信息存储对象
        UserLoginInfoVo userLoginInfo=new UserLoginInfoVo();

        //获取用户的其他信息
        User userInfo = userService.getOne(queryWrapper);

        //通过用户信息生成对应的token令牌
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("userName",user.getUsername());
        payload.put("name",user.getName());
        payload.put("userId", userInfo.getId());
        String token= JwtUtils.generateJwt(payload);
        //设置token到vo中
        userLoginInfo.setToken(token);
        //拷贝其他属性的值到vo中
        BeanUtil.copyProperties(userInfo, userLoginInfo);

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