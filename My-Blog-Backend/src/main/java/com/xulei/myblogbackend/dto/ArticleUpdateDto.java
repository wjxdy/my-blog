package com.xulei.myblogbackend.dto;

import com.xulei.myblogbackend.entity.Article;
import lombok.Data;

import java.util.List;
@Data
public class ArticleUpdateDto {

    private Article article;

    private List<String> articleTags;

}
