package com.xulei.myblogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xulei.myblogbackend.entity.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 音乐Mapper接口
 */
@Mapper
public interface MusicMapper extends BaseMapper<Music> {

    /**
     * 查询所有音乐，按排序顺序
     */
    List<Music> selectAllOrderBySort();
}
