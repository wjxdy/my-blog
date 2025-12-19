package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ArticleTag对象", description = "")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章标签id
     */
    @TableId(value ="article_tag_id" ,type = IdType.ASSIGN_UUID)
    @ApiModelProperty("文章标签id")
    private String articleTagId;

    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    private String articleTagName;

    /**
     * 添加标签时间
     */
    @ApiModelProperty("添加标签时间")
    private LocalDateTime artcleTagAddTime;
}
