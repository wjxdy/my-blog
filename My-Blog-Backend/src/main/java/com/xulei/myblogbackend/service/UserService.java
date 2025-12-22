package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.RegisterDto;
import com.xulei.myblogbackend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xulei.myblogbackend.vo.UserLoginInfoVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
public interface UserService extends IService<User> {

    void register(RegisterDto registerDto) throws BaseException;

    UserLoginInfoVo login(User user) throws BaseException;
}
