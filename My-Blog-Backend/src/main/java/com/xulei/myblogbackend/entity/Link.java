package com.xulei.myblogbackend.entity;

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
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 友情链接id
     */
    @TableId("link_id")
    private String linkId;

    /**
     * 链接标题
     */
    private String linkTitle;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 链接logo
     */
    private String linkLogoUrl;

    /**
     * 链接添加时间
     */
    private LocalDateTime linkAddTime;
}
