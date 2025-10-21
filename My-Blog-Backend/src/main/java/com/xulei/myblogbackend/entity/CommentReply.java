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
@TableName("comment_reply")
@ApiModel(value = "CommentReply对象", description = "")
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论回复id
     */
    @ApiModelProperty("评论回复id")
    @TableId("comment_reply_id")
    private String commentReplyId;

    /**
     * 评论id
     */
    @ApiModelProperty("评论id")
    private String commentId;

    /**
     * 回复的人的id
     */
    @ApiModelProperty("回复的人的id")
    private String replyUsername;

    /**
     * 继续回复的人的id
     */
    @ApiModelProperty("继续回复的人的id")
    private String secondlyUserId;

    /**
     * 回复的时间
     */
    @ApiModelProperty("回复的时间")
    private LocalDateTime commentReplyAddTime;
}
