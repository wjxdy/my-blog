package com.xulei.myblogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.entity.ArticleTagList;
import com.xulei.myblogbackend.mapper.ArticleTagListMapper;
import com.xulei.myblogbackend.service.ArticleTagListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulei
 * @since 2025-10-10
 */
@Service
@Slf4j
public class ArticleTagListServiceImpl extends ServiceImpl<ArticleTagListMapper, ArticleTagList> implements ArticleTagListService {


    @Transactional(rollbackFor = BaseException.class)
    @Override
    public void addTagList(List<ArticleTagList> articleTagList) throws BaseException {
        if (CollectionUtils.isEmpty(articleTagList)) {
            throw new BaseException("标签列表是空的");
        }

        boolean insertSuccess = this.saveBatch(articleTagList, 10);
        if (!insertSuccess) {
            log.error("批量插入ArticleTagList失败，数据量：{}", articleTagList.size());
            throw new BaseException("批量插入标签关联列表失败");
        }
    }


    @Override
    public void deleteTagList(String articleId) throws BaseException {
        LambdaQueryWrapper<ArticleTagList> queryWrapper = new LambdaQueryWrapper<ArticleTagList>()
                .eq(ArticleTagList::getArticleId, articleId);
        try {
            boolean remove = this.remove(queryWrapper);
        } catch (Exception e) {
            throw new BaseException("文章删除失败");
        }
    }
}
