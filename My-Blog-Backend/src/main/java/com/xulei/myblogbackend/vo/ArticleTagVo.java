package com.xulei.myblogbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTagVo {

    /**
     * 标签id
     */
    private String articleTagId;

    /**
     * 标签名称
     */
    private String articleTagName;
}
