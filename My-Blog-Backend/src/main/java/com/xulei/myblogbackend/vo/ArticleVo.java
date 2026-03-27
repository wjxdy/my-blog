package com.xulei.myblogbackend.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章列表VO，包含标签信息
 */
@Data
public class ArticleVo {

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 对应用户的username
     */
    private String username;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章添加时间
     */
    private LocalDateTime articleAddTime;

    /**
     * 文章内容（预览）
     */
    private String articleContext;

    /**
     * 点赞次数
     */
    private Integer articleGoodNumber;

    /**
     * 观看次数
     */
    private Integer articleLookNumber;

    /**
     * 收藏次数
     */
    private Integer articleCollectionNumber;
    
    /**
     * 文章标签列表
     */
    private List<ArticleTagVo> tags;
}
