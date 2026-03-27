package com.xulei.myblogbackend.controller;

import com.xulei.myblogbackend.dto.ArticleUpdateDto;
import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.ArticleDto;
import com.xulei.myblogbackend.dto.PageInfoDto;
import com.xulei.myblogbackend.entity.Article;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.service.ArticleService;
import com.xulei.myblogbackend.service.ArticleTagListService;
import com.xulei.myblogbackend.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private ArticleTagListService articleTagListService;

    @GetMapping("/getLog")
    public Result getLog(){
        Article article = articleService.getLog();
        return Result.success(article);
    }


    @DeleteMapping("/deleteArticle/{articleId}")
    public Result deleteArticleById(@PathVariable String articleId){
        try {
            articleService.deleteArticleById(articleId);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok("删除成功");
    }


    @GetMapping
    public Result<List<Article>> getArticleList(){
        List<Article> list = articleService.list();
        return Result.success(list);
    }


    @PostMapping("/list")
    public Result<PageInfoDto<List<ArticleVo>>> getArticlePage(@RequestBody PageInfoDto pageInfoDto){
        PageInfoDto<List<ArticleVo>> listPageInfoDto = articleService.getArticlePage(pageInfoDto);
        return Result.success(listPageInfoDto);
    }


    @GetMapping("/{id}")
    public Result<Map<String, Object>> getArticleById(@PathVariable("id") String id){
        Article article = articleService.getById(id);
        if (article == null) {
            return Result.fail("文章不存在");
        }
        
        // 获取文章的标签列表
        List<String> tagIds = articleTagListService.getTagIdsByArticleId(id);
        
        // 构建返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("articleId", article.getArticleId());
        result.put("username", article.getUsername());
        result.put("articleTitle", article.getArticleTitle());
        result.put("articleIntro", article.getArticleIntro());
        result.put("articleAddTime", article.getArticleAddTime());
        result.put("articleUpdateTime", article.getArticleUpdateTime());
        result.put("articleContext", article.getArticleContext());
        result.put("articleGoodNumber", article.getArticleGoodNumber());
        result.put("articleLookNumber", article.getArticleLookNumber());
        result.put("articleCollectionNumber", article.getArticleCollectionNumber());
        result.put("articleTags", tagIds);
        
        return Result.success(result);
    }


    @PostMapping("/add")
    public Result addArticle(@RequestBody ArticleDto articleDto){
        try {
            articleService.addAeticle(articleDto);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok();
    }


    @PutMapping("/updateArticle")
    public Result updateArticle(@RequestBody ArticleUpdateDto articleUpdateDto){
        try {
            articleService.updateArticle(articleUpdateDto);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok();
    }

}
