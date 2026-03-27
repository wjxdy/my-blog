package com.xulei.myblogbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xulei.myblogbackend.entity.Music;
import com.xulei.myblogbackend.excpetion.BaseException;

import java.util.List;

/**
 * 音乐服务接口
 */
public interface MusicService extends IService<Music> {

    /**
     * 获取所有音乐列表
     */
    List<Music> getMusicList();

    /**
     * 上传音乐
     */
    Music uploadMusic(String musicName, String musicUrl, String musicArtist) throws BaseException;

    /**
     * 删除音乐
     */
    void deleteMusic(String musicId) throws BaseException;

    /**
     * 获取音乐数量
     */
    long getMusicCount();
}
