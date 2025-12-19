package com.xulei.myblogbackend.entity;

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
@TableName("ad_type")
@Accessors(chain = true)
public class AdType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 广告类型id
     */
    @TableId("ad_type_id")
    private String adTypeId;

    /**
     * 广告类型名称
     */
    private String adTypeTitle;

    /**
     * 广告标识（首页标题数字广告，轮播图光嘎）
     */
    private String adTypeTag;

    /**
     * 广告类型排序，越小越靠前
     */
    private Integer adTypeSort;

    /**
     * 广告类型添加时间
     */
    private LocalDateTime adTypeAddTime;
}
