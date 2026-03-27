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
 * 音乐实体类
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("music")
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 音乐ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String musicId;

    /**
     * 音乐名称
     */
    private String musicName;

    /**
     * 音乐URL（OSS地址）
     */
    private String musicUrl;

    /**
     * 艺术家/歌手
     */
    private String musicArtist;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
