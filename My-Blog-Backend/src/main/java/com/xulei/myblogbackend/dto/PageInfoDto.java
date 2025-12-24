package com.xulei.myblogbackend.dto;

import com.xulei.myblogbackend.vo.ArticleTagVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfoDto<T> {

    private Integer pageSize;
    private Long pageTotal;
    private Integer currentPage;
    private T data;
    private String condition;
    private List<ArticleTagVo> tags;


}
