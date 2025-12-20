package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)

public class Article {


    /**
     * 文章id
     */
    @TableId(type = IdType.ASSIGN_UUID)

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
     * 文章介绍
     */

    private String articleIntro;

    /**
     * 文章修改时间
     */

    private LocalDateTime articleUpdateTime;


    /**
     * 文章添加时间
     */
    private LocalDateTime articleAddTime;

    /**
     * 文章内容
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
}
