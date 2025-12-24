package com.xulei.myblogbackend.controller;

import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.entity.ArticleTag;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.service.ArticleTagService;
import com.xulei.myblogbackend.vo.ArticleTagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class ArticleTagController {

    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping
    public Result getTagList(){
        List<ArticleTagVo> list = null;
        try {
            list = articleTagService.getTagList();
        } catch (Exception e) {
            return Result.fail("查询错误");
        }
        return Result.success(list);
    }


    @GetMapping("/add")
    public Result addTag(@RequestParam("tagName") String tagInfo){
        ArticleTagVo tagVo =null;
        try {
            tagVo = articleTagService.addTag(tagInfo);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.success(tagVo);
    }

    @DeleteMapping("/delete/{tagId}")
    public Result deleteTag(@PathVariable("tagId") String tagId){
        try {
            articleTagService.deleteTag(tagId);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
        return Result.ok();
    }





}
