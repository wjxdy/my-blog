package com.xulei.myblogbackend.service.impl;

import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.entity.ArticleTag;
import com.xulei.myblogbackend.entity.LoginInfo;
import com.xulei.myblogbackend.mapper.ArticleTagMapper;
import com.xulei.myblogbackend.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.myblogbackend.utils.UserHolder;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public List<ArticleTag> getTagList() {
        return this.list();
    }

    @Override
    public void addTag(String tagInfo) throws BaseException {
        LoginInfo user = UserHolder.getUser();
        if (user == null) {
            throw new BaseException("未登录");
        }
        if (user.getUserId()!=1) {
            throw new BaseException("只有管理员可以添加");
        }

        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleTagName(tagInfo);
        articleTag.setArtcleTagAddTime(LocalDateTime.now());
        boolean save = this.save(articleTag);
        if (!save) {
            throw new BaseException("保存失败");
        }
    }

    @Override
    public void deleteTag(String tagId) throws BaseException {
        LoginInfo user = UserHolder.getUser();
        if (user == null) {
            throw new BaseException("未登录");
        }
        if (user.getUserId()!=1) {
            throw new BaseException("只有管理员可以添加");
        }

    }
}
