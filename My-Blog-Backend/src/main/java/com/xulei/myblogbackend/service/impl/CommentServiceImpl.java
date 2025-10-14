package com.xulei.myblogbackend.service.impl;

import com.xulei.myblogbackend.entity.Comment;
import com.xulei.myblogbackend.mapper.CommentMapper;
import com.xulei.myblogbackend.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
