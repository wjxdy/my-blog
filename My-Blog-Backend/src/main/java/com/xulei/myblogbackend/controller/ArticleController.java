package com.xulei.myblogbackend.controller;

import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.dto.ArticleDto;
import com.xulei.myblogbackend.dto.PageInfoDto;
import com.xulei.myblogbackend.entity.Article;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

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
    public Result<PageInfoDto<List<Article>>> getArticlePage(@RequestBody PageInfoDto pageInfoDto){
        PageInfoDto<List<Article>> listPageInfoDto = articleService.getArticlePage(pageInfoDto);
        return Result.success(listPageInfoDto);
    }


    @GetMapping("/{id}")
    public Result<Article> getArticleById(@PathVariable("id") String id){
        Article article = articleService.getById(id);
        return Result.success(article);
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
    public Result updateArticle(@RequestBody Article article){
        try {
            articleService.updateArticle(article);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok();
    }

}
