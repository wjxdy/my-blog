package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@Accessors(chain = true)
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

    private String username;

    /**
     * 密码
     */

    private String password;

    /**
     * 账号创建时间
     */

    private LocalDateTime registerTime;

    /**
     * 用户图像路经
     */

    private String imgUrl;
}
