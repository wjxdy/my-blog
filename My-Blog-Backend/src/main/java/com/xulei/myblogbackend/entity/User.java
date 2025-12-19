package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@Accessors(chain = true)
@ApiModel(value = "Reader对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 账号名字
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 账号创建时间
     */
    @ApiModelProperty("账号创建时间 ")
    private LocalDateTime registerTime;

    /**
     * 用户图像路经
     */
    @ApiModelProperty("头像 ")
    private String imgUrl;
}
