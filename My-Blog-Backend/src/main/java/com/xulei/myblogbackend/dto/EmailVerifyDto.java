package com.xulei.myblogbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailVerifyDto {

    private String emailAddress;

    private String code;


}
