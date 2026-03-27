package com.xulei.myblogbackend.controller;


import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.RegisterDto;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.entity.User;
import com.xulei.myblogbackend.service.UserService;
import com.xulei.myblogbackend.entity.LoginInfo;
import com.xulei.myblogbackend.utils.UserHolder;
import com.xulei.myblogbackend.vo.UserLoginInfoVo;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    
    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo() {
        LoginInfo loginUser = UserHolder.getUser();
        if (loginUser == null) {
            return Result.fail("用户未登录");
        }
        User user = userService.getUserByUsername(loginUser.getUserName());
        if (user == null) {
            return Result.fail("用户不存在");
        }
        // 为安全起见，隐藏密码
        user.setPassword(null);
        return Result.success(user);
    }
    
    /**
     * 获取博主信息（公开接口，不需要登录）
     */
    @GetMapping("/public/info")
    public Result<User> getPublicUserInfo() {
        // 获取第一个用户作为博主（或者可以根据配置指定特定用户）
        User user = userService.getById(1);
        if (user == null) {
            return Result.fail("博主信息不存在");
        }
        // 为安全起见，隐藏敏感信息
        user.setPassword(null);
        user.setEmail(null);
        user.setPhone(null);
        return Result.success(user);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    public Result<User> updateUserInfo(@RequestBody User user) {
        LoginInfo loginUser = UserHolder.getUser();
        if (loginUser == null) {
            return Result.fail("用户未登录");
        }
        // 确保只能修改自己的信息
        user.setUsername(loginUser.getUserName());
        
        log.info("接收到更新请求: username={}, name={}, imgUrl={}, email={}, phone={}, sex={}",
                user.getUsername(), user.getName(), user.getImgUrl(), 
                user.getEmail(), user.getPhone(), user.getSex());
        
        try {
            User updatedUser = userService.updateUserInfo(user);
            // 隐藏密码
            updatedUser.setPassword(null);
            return Result.success(updatedUser);
        } catch (BaseException e) {
            log.error("更新用户信息失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        }
    }


}