package com.xulei.myblogbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xulei.myblogbackend.dto.ArticleUpdateDto;
import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.ArticleDto;
import com.xulei.myblogbackend.dto.PageInfoDto;
import com.xulei.myblogbackend.entity.Article;
import com.xulei.myblogbackend.entity.ArticleTagList;
import com.xulei.myblogbackend.entity.LoginInfo;
import com.xulei.myblogbackend.mapper.ArticleMapper;
import com.xulei.myblogbackend.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.myblogbackend.service.ArticleTagListService;
import com.xulei.myblogbackend.service.ArticleTagService;
import com.xulei.myblogbackend.utils.UserHolder;
import com.xulei.myblogbackend.vo.ArticleTagVo;
import com.xulei.myblogbackend.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    @Autowired
    private ArticleTagListService articleTagListService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ArticleMapper articleMapper;


    @Transactional
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

        // 先删除与文章关联的标签
        articleTagListService.deleteTagList(articleId);
        
        // 再删除文章主体
        boolean isDelete = this.removeById(articleId);
        if (!isDelete) {
            throw new BaseException("删除失败");
        }

    }


    @Override
    public PageInfoDto<List<ArticleVo>> getArticlePage(PageInfoDto pageInfoDto) {
        PageHelper.startPage(pageInfoDto.getCurrentPage(), pageInfoDto.getPageSize(),"article_add_time DESC");
        List<ArticleTagVo> tags = pageInfoDto.getTags();
        List<String> tagIdList = tags.stream().map(ArticleTagVo::getArticleTagId).toList();

        // 第一步：查询文章列表（不带标签，避免JOIN导致分页错乱）
        List<Article> articleList = articleMapper.getArticleInfo(tagIdList, pageInfoDto.getCondition());
        
        Page<Article> page = (Page<Article>) articleList;
        
        // 第二步：如果有文章，查询这些文章的标签
        List<ArticleVo> resultList = new ArrayList<>();
        if (!articleList.isEmpty()) {
            List<String> articleIds = articleList.stream()
                    .map(Article::getArticleId)
                    .collect(Collectors.toList());
            
            // 批量查询文章标签关联
            List<ArticleTagList> tagRelations = articleTagListService.list(
                    new LambdaQueryWrapper<ArticleTagList>()
                            .in(ArticleTagList::getArticleId, articleIds)
            );
            
            // 获取所有标签ID
            List<String> allTagIds = tagRelations.stream()
                    .map(ArticleTagList::getArticleTagId)
                    .distinct()
                    .collect(Collectors.toList());
            
            // 查询标签详情
            final Map<String, String> tagNameMap = new HashMap<>();
            if (!allTagIds.isEmpty()) {
                // 使用ArticleTagService查询标签名称
                List<com.xulei.myblogbackend.entity.ArticleTag> tagEntities = 
                        articleTagService.listByIds(allTagIds);
                tagNameMap.putAll(tagEntities.stream()
                        .collect(Collectors.toMap(
                                com.xulei.myblogbackend.entity.ArticleTag::getArticleTagId,
                                com.xulei.myblogbackend.entity.ArticleTag::getArticleTagName,
                                (v1, v2) -> v1
                        )));
            }
            
            // 按文章ID分组标签
            Map<String, List<ArticleTagVo>> articleTagsMap = tagRelations.stream()
                    .collect(Collectors.groupingBy(
                            ArticleTagList::getArticleId,
                            Collectors.mapping(relation -> {
                                ArticleTagVo vo = new ArticleTagVo();
                                vo.setArticleTagId(relation.getArticleTagId());
                                vo.setArticleTagName(tagNameMap.getOrDefault(relation.getArticleTagId(), ""));
                                return vo;
                            }, Collectors.toList())
                    ));
            
            // 组装ArticleVo
            for (Article article : articleList) {
                ArticleVo vo = new ArticleVo();
                vo.setArticleId(article.getArticleId());
                vo.setUsername(article.getUsername());
                vo.setArticleTitle(article.getArticleTitle());
                vo.setArticleAddTime(article.getArticleAddTime());
                vo.setArticleContext(article.getArticleContext());
                vo.setArticleGoodNumber(article.getArticleGoodNumber());
                vo.setArticleLookNumber(article.getArticleLookNumber());
                vo.setArticleCollectionNumber(article.getArticleCollectionNumber());
                vo.setTags(articleTagsMap.getOrDefault(article.getArticleId(), new ArrayList<>()));
                resultList.add(vo);
            }
        }
        
        PageInfoDto<List<ArticleVo>> listPageInfoDto = new PageInfoDto<>();
        listPageInfoDto.setPageTotal(page.getTotal());
        listPageInfoDto.setData(resultList);
        listPageInfoDto.setCurrentPage(pageInfoDto.getCurrentPage());

        return listPageInfoDto;
    }


    @Override
    @Transactional
    public void addAeticle(ArticleDto articleDto) throws BaseException {
        LoginInfo user = UserHolder.getUser();
        if (user == null) {
            throw new BaseException("用户名为null");
        }
        //给article赋值
        Article article = BeanUtil.copyProperties(articleDto, Article.class);
        // 生成无连字符UUID（32个字符）
        String articleId = UUID.randomUUID().toString().replace("-", "");
        article.setArticleId(articleId);
        article.setArticleAddTime(LocalDateTime.now());
        article.setUsername(user.getUserName());
        if (article.getArticleTitle().isBlank()) {
            throw new BaseException("用户标题为空");
        }
        boolean save = this.save(article);
        if (!save) {
            throw new BaseException("添加失败");
        }
        if (articleDto.getArticleTags().isEmpty()){
            return;
        }
        //给taglist赋值
        List<ArticleTagList> list = articleDto.getArticleTags().stream().map(item -> {
            ArticleTagList articleTagList = new ArticleTagList();
            articleTagList.setArticleId(articleId);
            articleTagList.setArticleTagId(item);
            return articleTagList;
        }).toList();
        //批量添加
        articleTagListService.addTagList(list);
    }

    @Transactional
    @Override
    public void updateArticle(ArticleUpdateDto articleUpdateDto) throws BaseException {
        LoginInfo user = UserHolder.getUser();
        if (user == null) {
            throw new BaseException("请先登录再更改");
        }

        Article article = articleUpdateDto.getArticle();
        if (article == null) {
            throw new BaseException("文章信息不能为空");
        }
        if (!article.getUsername().equals(user.getUserName())) {
            throw new BaseException("非本人文章无法修改");
        }
        
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getArticleId, article.getArticleId());
        
        // 更新文章内容（如果提供了）
        if (article.getArticleContext() != null && !article.getArticleContext().isEmpty()) {
            updateWrapper.set(Article::getArticleContext, article.getArticleContext());
        }
        
        // 更新文章简介
        if (article.getArticleIntro() != null) {
            updateWrapper.set(Article::getArticleIntro, article.getArticleIntro());
        }
        
        // 更新时间
        updateWrapper.set(Article::getArticleUpdateTime, LocalDateTime.now());
        
        // 更新标题（如果提供了）
        if (article.getArticleTitle() != null && !article.getArticleTitle().isEmpty()) {
            updateWrapper.set(Article::getArticleTitle, article.getArticleTitle());
        }
        
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BaseException("修改失败");
        }
        
        // 处理标签
        List<String> articleTags = articleUpdateDto.getArticleTags();
        
        // 删除旧标签关联（无论新标签是否为空，都要先删除旧的）
        if (article.getArticleId() != null) {
            articleTagListService.deleteTagList(article.getArticleId());
        }
        
        // 如果有新标签，添加新标签关联
        if (articleTags != null && !articleTags.isEmpty()) {
            List<ArticleTagList> list = articleTags.stream().map(item -> {
                ArticleTagList articleTagList = new ArticleTagList();
                articleTagList.setArticleId(article.getArticleId());
                articleTagList.setArticleTagId(item);
                return articleTagList;
            }).collect(Collectors.toList());
            
            articleTagListService.addTagList(list);
        }
    }

    @Override
    public Article getLog() {
        Article article = this.getById("1");
        return article;
    }
}
