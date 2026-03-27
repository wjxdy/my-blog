package com.xulei.myblogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xulei.myblogbackend.entity.Article;
import com.xulei.myblogbackend.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> getArticleInfo(@Param("tagIds") List<String> tags, @Param("titleKeyword") String condition);
    
    /**
     * 查询文章列表（带标签信息）
     */
    List<ArticleVo> getArticleListWithTags(@Param("tagIds") List<String> tags, @Param("titleKeyword") String condition);
}
