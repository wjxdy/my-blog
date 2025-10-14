package com.xulei.myblogbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfoDto<T> {

    private Integer pageSize;
    private Long pageTotal;
    private Integer currentPage;
    private T data;


}
