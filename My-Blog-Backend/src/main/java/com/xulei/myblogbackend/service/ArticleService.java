package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.ArticleDto;
import com.xulei.myblogbackend.dto.PageInfoDto;
import com.xulei.myblogbackend.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
public interface ArticleService extends IService<Article> {

    void deleteArticleById(String articleId) throws BaseException;

    PageInfoDto<List<Article>> getArticlePage(PageInfoDto pageInfoDto);

    void addAeticle(ArticleDto articleDto) throws BaseException;

    void updateArticle(Article article) throws BaseException;

    Article getLog();
}
