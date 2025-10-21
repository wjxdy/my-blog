package com.xulei.myblogbackend.service.impl;

import com.xulei.myblogbackend.entity.User;
import com.xulei.myblogbackend.mapper.ReaderMapper;
import com.xulei.myblogbackend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<ReaderMapper, User> implements UserService {

}
