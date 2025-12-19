package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.*;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 广告id
     */
    @TableId("ad_id")
    private String adId;

    /**
     * 广告类型
     */
    private String adTypeId;

    /**
     * 广告标题
     */
    private String adTitle;

    /**
     * 广告url地址
     */
    private String adUrl;

    /**
     * 广告排序，越小越靠前
     */
    private Integer adSort;

    /**
     * 广告开始时间
     */
    private LocalDateTime adBeginTime;

    /**
     * 广告结束时间
     */
    private LocalDateTime adEndTime;

    /**
     * 广告添加时间
     */
    private LocalDateTime adAddTime;
}
