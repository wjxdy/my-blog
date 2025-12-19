package com.xulei.myblogbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginInfoVo {

    private Integer id;

    private String name;

    private String token;

    private String username;

}
