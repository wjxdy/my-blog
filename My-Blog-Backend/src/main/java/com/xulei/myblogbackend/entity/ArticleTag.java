package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("article_tag")

public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章标签id
     */
    @TableId(value ="article_tag_id" ,type = IdType.ASSIGN_UUID)

    private String articleTagId;

    /**
     * 标签名称
     */

    private String articleTagName;

    /**
     * 添加标签时间
     */

    private LocalDateTime artcleTagAddTime;
}
