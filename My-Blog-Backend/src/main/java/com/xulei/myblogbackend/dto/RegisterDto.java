package com.xulei.myblogbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RegisterDto {


    private String code;
    private String name;

    private String email;
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;



}
