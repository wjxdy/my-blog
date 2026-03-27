package com.xulei.myblogbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.RegisterDto;
import com.xulei.myblogbackend.entity.User;
import com.xulei.myblogbackend.mapper.ReaderMapper;
import com.xulei.myblogbackend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.myblogbackend.utils.JwtUtils;
import com.xulei.myblogbackend.vo.UserLoginInfoVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.xulei.myblogbackend.utils.StringContext.EMAIL_VERIFIED_FLAG;
import static com.xulei.myblogbackend.utils.StringContext.REDIS_EMAIL_SEND_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<ReaderMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void register(RegisterDto registerDto) throws BaseException {
        // 1. 入参非空校验（配合@Valid注解，这里做补充防御）
        if (registerDto == null || StrUtil.isBlank(registerDto.getEmail())
                || StrUtil.isBlank(registerDto.getUsername())
                || StrUtil.isBlank(registerDto.getPassword())
                || StrUtil.isBlank(registerDto.getName())) {
            throw new BaseException("用户名、用户ID、邮箱、密码不能为空");
        }

        String email = registerDto.getEmail();
        String key = REDIS_EMAIL_SEND_KEY + email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if (!EMAIL_VERIFIED_FLAG.equals(code)){
            throw new BaseException("校验错误或已过期");
        }
        boolean usernameExists = this.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, registerDto.getUsername()));
        if (usernameExists) {
            throw new BaseException("账号ID已存在，请设置新的账号ID");
        }

        // 4. 邮箱唯一性校验（补充必要校验：避免同一邮箱注册多个账号）
        boolean emailExists = this.exists(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email));
        if (emailExists) {
            throw new BaseException("该邮箱已注册账号，请直接登录");
        }

        User user = BeanUtil.copyProperties(registerDto, User.class);
        user.setRegisterTime(LocalDateTime.now());
        boolean save = this.save(user);
        if (save) {
            throw new BaseException("添加失败");
        }

    }

    @Override
    public UserLoginInfoVo login(User user) throws BaseException {
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername())
                .eq(User::getPassword,user.getPassword());
        boolean exists = this.exists(queryWrapper);
        if(!exists){
            throw new BaseException("账号或密码错误");
        }
        //获取用户的其他信息
        User userOtherInfo = this.getOne(queryWrapper);

        //创建返回给前端的信息存储对象
        UserLoginInfoVo userInfo=new UserLoginInfoVo();

        //通过用户信息生成对应的token令牌
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("userName",userOtherInfo.getUsername());
        payload.put("name",userOtherInfo.getName());
        payload.put("userId", userOtherInfo.getId());
        String token= JwtUtils.generateJwt(payload);
        //设置token到vo中
        userInfo.setToken(token);
        //拷贝其他属性的值到vo中
        BeanUtil.copyProperties(userOtherInfo,userInfo);
        log.info("登录vo信息： {}",userInfo);
        return userInfo;
    }
    
    @Override
    public User updateUserInfo(User user) throws BaseException {
        if (user == null || user.getUsername() == null) {
            throw new BaseException("用户信息不能为空");
        }
        
        log.info("更新用户信息, username: {}, name: {}, bio: {}, githubUrl: {}", 
                user.getUsername(), user.getName(), user.getBio(), user.getGithubUrl());
        
        // 先查询用户是否存在
        User existingUser = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (existingUser == null) {
            throw new BaseException("用户不存在");
        }
        
        // 使用 LambdaUpdateWrapper 更新指定字段
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, existingUser.getId());
        
        // 只更新允许修改的字段（区分 null 和空字符串）
        if (user.getName() != null) {
            updateWrapper.set(User::getName, user.getName());
        }
        if (user.getImgUrl() != null) {
            updateWrapper.set(User::getImgUrl, user.getImgUrl());
            log.info("更新头像URL: {}", user.getImgUrl());
        }
        if (user.getEmail() != null) {
            updateWrapper.set(User::getEmail, user.getEmail());
        }
        if (user.getSex() != null) {
            updateWrapper.set(User::getSex, user.getSex());
        }
        if (user.getPhone() != null) {
            updateWrapper.set(User::getPhone, user.getPhone());
        }
        if (user.getBio() != null) {
            updateWrapper.set(User::getBio, user.getBio());
            log.info("更新bio: {}", user.getBio());
        }
        if (user.getGithubUrl() != null) {
            updateWrapper.set(User::getGithubUrl, user.getGithubUrl());
            log.info("更新githubUrl: {}", user.getGithubUrl());
        }
        
        // 执行更新
        boolean update = this.update(updateWrapper);
        log.info("更新结果: {}", update);
        
        if (!update) {
            throw new BaseException("更新用户信息失败");
        }
        
        // 返回更新后的用户信息
        User updatedUser = this.getById(existingUser.getId());
        log.info("更新后的用户信息, bio: {}, githubUrl: {}", updatedUser.getBio(), updatedUser.getGithubUrl());
        return updatedUser;
    }
    
    @Override
    public User getUserByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }
}
