package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.Excpetion.BaseException;
import com.xulei.myblogbackend.entity.ArticleTagList;
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
public interface ArticleTagListService extends IService<ArticleTagList> {

    void addTagList(List<ArticleTagList> articleTagList) throws BaseException;

    void deleteTagList(String articleId,String tagId) throws BaseException;

}
