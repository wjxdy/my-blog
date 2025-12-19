package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Link对象", description = "")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 友情链接id
     */
    @TableId("link_id")
    @ApiModelProperty("友情链接id")
    private String linkId;

    /**
     * 链接标题
     */
    @ApiModelProperty("链接标题")
    private String linkTitle;

    /**
     * 链接地址
     */
    @ApiModelProperty("链接地址")
    private String linkUrl;

    /**
     * 链接logo
     */
    @ApiModelProperty("链接logo")
    private String linkLogoUrl;

    /**
     * 链接添加时间
     */
    @ApiModelProperty("链接添加时间")
    private LocalDateTime linkAddTime;
}
