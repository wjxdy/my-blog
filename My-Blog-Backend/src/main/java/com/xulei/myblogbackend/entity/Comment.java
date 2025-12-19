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

public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章评论id
     */
    @TableId("comment_id")

    private String commentId;

    /**
     * 文章id
     */

    private String articleId;

    /**
     * 读者账号
     */

    private String username;

    /**
     * 评论时间
     */

    private LocalDateTime commentTime;

    /**
     * 评论点赞次数
     */

    private Integer commentGoodNumber;
}
