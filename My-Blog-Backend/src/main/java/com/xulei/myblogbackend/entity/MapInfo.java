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
 * @since 2025-12-06
 */
@Getter
@Setter
@ToString
@TableName("map_info")
@Accessors(chain = true)
public class MapInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String ipLocation;

    private String accuracy;

    private String browser;

    private String deviceType;

    private String latitude;

    private String longitude;

    private String os;

    private String ip;

    private LocalDateTime createTime;


}
