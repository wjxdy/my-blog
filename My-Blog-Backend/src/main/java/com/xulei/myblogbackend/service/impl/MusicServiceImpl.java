package com.xulei.myblogbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.myblogbackend.entity.Music;
import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.mapper.MusicMapper;
import com.xulei.myblogbackend.service.MusicService;
import com.xulei.myblogbackend.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 音乐服务实现类
 */
@Service
@Slf4j
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Autowired
    private MusicMapper musicMapper;
    
    @Autowired
    private OssService ossService;

    @Override
    public List<Music> getMusicList() {
        return musicMapper.selectAllOrderBySort();
    }

    @Override
    public Music uploadMusic(String musicName, String musicUrl, String musicArtist) throws BaseException {
        // 检查数量限制
        long count = this.count();
        if (count >= 5) {
            throw new BaseException("最多只能上传5首歌曲，请先删除其他歌曲");
        }

        Music music = new Music();
        music.setMusicName(musicName);
        music.setMusicUrl(musicUrl);
        music.setMusicArtist(musicArtist);
        music.setSortOrder((int) count); // 默认排在最后
        music.setCreateTime(LocalDateTime.now());

        boolean save = this.save(music);
        if (!save) {
            throw new BaseException("保存音乐信息失败");
        }

        log.info("上传音乐成功: {}", musicName);
        return music;
    }

    @Override
    public void deleteMusic(String musicId) throws BaseException {
        Music music = this.getById(musicId);
        if (music == null) {
            throw new BaseException("音乐不存在");
        }
        
        // 获取音乐URL用于删除OSS文件
        String musicUrl = music.getMusicUrl();

        // 先删除数据库记录
        boolean remove = this.removeById(musicId);
        if (!remove) {
            throw new BaseException("删除音乐失败");
        }
        
        // 删除OSS上的文件（异步删除，不影响主流程）
        if (musicUrl != null && !musicUrl.isEmpty()) {
            try {
                boolean deleted = ossService.deleteFile(musicUrl);
                if (deleted) {
                    log.info("删除OSS文件成功: {}", musicUrl);
                } else {
                    log.warn("删除OSS文件失败或文件不存在: {}", musicUrl);
                }
            } catch (Exception e) {
                log.error("删除OSS文件异常: {}", musicUrl, e);
                // 不抛出异常，因为数据库记录已删除
            }
        }

        log.info("删除音乐成功: {}", musicId);
    }

    @Override
    public long getMusicCount() {
        return this.count();
    }
}
