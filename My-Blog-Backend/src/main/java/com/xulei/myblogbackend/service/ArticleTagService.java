package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xulei.myblogbackend.vo.ArticleTagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
public interface ArticleTagService extends IService<ArticleTag> {

    List<ArticleTagVo> getTagList();

    ArticleTagVo addTag(String tagInfo) throws BaseException;

    void deleteTag(String tagId) throws BaseException;
}
