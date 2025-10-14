package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Ad对象", description = "")
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 广告id
     */
    @TableId("ad_id")
    @ApiModelProperty("广告id")
    private String adId;

    /**
     * 广告类型
     */
    @ApiModelProperty("广告类型")
    private String adTypeId;

    /**
     * 广告标题
     */
    @ApiModelProperty("广告标题")
    private String adTitle;

    /**
     * 广告url地址
     */
    @ApiModelProperty("广告url地址")
    private String adUrl;

    /**
     * 广告排序，越小越靠前
     */
    @ApiModelProperty("广告排序，越小越靠前")
    private Integer adSort;

    /**
     * 广告开始时间
     */
    @ApiModelProperty("广告开始时间")
    private LocalDateTime adBeginTime;

    /**
     * 广告结束时间
     */
    @ApiModelProperty("广告结束时间")
    private LocalDateTime adEndTime;

    /**
     * 广告添加时间
     */
    @ApiModelProperty("广告添加时间")
    private LocalDateTime adAddTime;
}
