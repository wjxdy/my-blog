package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Reader对象", description = "")
public class Reader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    /**
     * 读者种类
     */
    @ApiModelProperty("读者种类")
    private Integer categoryId;

    private Byte sex;

    private String wAddress;

    private String hAddress;

    private String phone;

    private String email;

    private LocalDateTime createTime;

    private String notes;

    /**
     * 读者账号
     */
    @ApiModelProperty("读者账号")
    private String username;

    /**
     * 读者密码
     */
    @ApiModelProperty("读者密码")
    private String password;

    /**
     * 读者账号创建时间 
     */
    @ApiModelProperty("读者账号创建时间 ")
    private LocalDateTime registerTime;
}
