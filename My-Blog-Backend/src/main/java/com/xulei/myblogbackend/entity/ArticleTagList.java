package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("article_tag_list")
@ApiModel(value = "ArticleTagList对象", description = "")
public class ArticleTagList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章对应标签表idi
     */
    @ApiModelProperty("文章对应标签表idi")
    @TableId("article_tag_list_id")
    private String articleTagListId;

    /**
     * 文章id
     */
    @ApiModelProperty("文章id")
    private String articleId;

    /**
     * 文章对应标签id
     */
    @ApiModelProperty("文章对应标签id")
    private String articleTagId;
}
