package com.xulei.myblogbackend.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xulei.myblogbackend.dto.PageInfoDto;
import com.xulei.myblogbackend.entity.Article;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @DeleteMapping("/deleteArticle/{articleId}")
    public Result deleteArticleById(@PathVariable String articleId){

        try {
            articleService.removeById(articleId);

            return Result.ok();

        } catch (Exception e) {

            return Result.fail("删除失败");

        }



    }

    @GetMapping
    public Result<List<Article>> getArticleList(){

        List<Article> list = articleService.list();
        Result<List<Article>> success = Result.success(list);

        return success;

    }
    @PostMapping("/list")
    public Result<PageInfoDto<List<Article>>> getArticlePage(@RequestBody PageInfoDto pageInfoDto){
        PageHelper.startPage(pageInfoDto.getCurrentPage(), pageInfoDto.getPageSize(),"article_add_time DESC");


        List<Article> list = articleService.list();
        Page<Article> page = (Page<Article>)list;
        PageInfoDto<List<Article>> listPageInfoDto = new PageInfoDto<>();
        listPageInfoDto.setPageTotal( page.getTotal());
        listPageInfoDto.setData(page.getResult());
        listPageInfoDto.setCurrentPage(pageInfoDto.getCurrentPage());

        return Result.success(listPageInfoDto);

    }
    @GetMapping("/{id}")
    public Result<Article> getArticleById(@PathVariable("id") String id){
        Article article = articleService.getById(id);
        return Result.success(article);
    }
    @PostMapping("/add")
    public Result addArticle(@RequestBody Article article){

        article.setArticleAddTime(LocalDateTime.now());
        article.setReaderUsername("test01");

        articleService.save(article);

        return Result.ok();
    }
    @PutMapping("/updateArticle")
    public Result updateArticle(@RequestBody Article article){
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getArticleId,article.getArticleId())
                        .set(!article.getArticleContext().isEmpty(),Article::getArticleContext,article.getArticleContext())
                        .set(Article::getArticleIntro,article.getArticleIntro())
                        .set(Article::getArticleUpdateTime,LocalDateTime.now())
                        .set(!article.getArticleTitle().isEmpty(),Article::getArticleTitle,article.getArticleTitle());

        articleService.update(updateWrapper);
        return Result.ok();
    }


}
