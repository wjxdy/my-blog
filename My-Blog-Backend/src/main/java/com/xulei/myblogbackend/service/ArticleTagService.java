package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List<ArticleTag> getTagList();

    void addTag(String tagInfo) throws BaseException;

    void deleteTag(String tagId) throws BaseException;
}
