package com.xulei.myblogbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDto {

    /**
     * 文章id
     */
    private String articleId;
    /**
     * 文章标题
     */

    private String articleTitle;
    /**
     * 文章介绍
     */

    private String articleIntro;
    /**
     * 文章内容
     */
    private String articleContext;

    /**
     * 标签集合
     */
    private List<String> articleTags;



}
