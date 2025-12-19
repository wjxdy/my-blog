package com.xulei.myblogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.dto.PageInfoDto;
import com.xulei.myblogbackend.entity.Article;
import com.xulei.myblogbackend.entity.LoginInfo;
import com.xulei.myblogbackend.mapper.ArticleMapper;
import com.xulei.myblogbackend.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.myblogbackend.utils.UserHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public void deleteArticleById(String articleId) throws BaseException {
        LoginInfo user = UserHolder.getUser();
        if (user == null) {
            throw new BaseException("请先登录再更改");
        }
        Article article = this.getById(articleId);
        if (!article.getUsername().equals(user.getUserName())) {
            throw new BaseException("非本人文章无法删除");
        }

        boolean isDelete = this.removeById(articleId);
        if (!isDelete) {
            throw new BaseException("添加失败");
        }

    }


    @Override
    public PageInfoDto<List<Article>> getArticlePage(PageInfoDto pageInfoDto) {
        PageHelper.startPage(pageInfoDto.getCurrentPage(), pageInfoDto.getPageSize(),"article_add_time DESC");

        List<Article> list = this.list();
        Page<Article> page = (Page<Article>)list;
        PageInfoDto<List<Article>> listPageInfoDto = new PageInfoDto<>();
        listPageInfoDto.setPageTotal( page.getTotal());

        List<Article> dataInfo = page.getResult().stream().map(article -> article.setArticleContext(null))
                .collect(Collectors.toList());

        listPageInfoDto.setData(dataInfo);
        listPageInfoDto.setCurrentPage(pageInfoDto.getCurrentPage());

        return listPageInfoDto;
    }


    @Override
    public void addAeticle(Article article) throws BaseException {
        LoginInfo user = UserHolder.getUser();
        if (user == null) {
            throw new BaseException("用户名为null");
        }
        article.setArticleAddTime(LocalDateTime.now());
        article.setUsername(user.getUserName());
        if (article.getArticleTitle().isBlank()) {
            throw new BaseException("用户标题为空");
        }
        boolean save = this.save(article);
        if (!save) {
            throw new BaseException("添加失败");
        }

    }

    @Override
    public void updateArticle(Article article) throws BaseException {
        LoginInfo user = UserHolder.getUser();
        if (user == null) {
            throw  new BaseException("请先登录再更改");
        }
        if (!article.getUsername().equals(user.getUserName())) {
            throw new BaseException("非本人文章无法修改");
        }
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getArticleId,article.getArticleId())
                .set(!article.getArticleContext().isEmpty(),Article::getArticleContext,article.getArticleContext())
                .set(Article::getArticleIntro,article.getArticleIntro())
                .set(Article::getArticleUpdateTime,LocalDateTime.now())
                .set(!article.getArticleTitle().isEmpty(),Article::getArticleTitle,article.getArticleTitle());

        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BaseException("修改失败");
        }
    }

    @Override
    public Article getLog() {
        Article article = this.getById("1");
        return article;
    }
}
