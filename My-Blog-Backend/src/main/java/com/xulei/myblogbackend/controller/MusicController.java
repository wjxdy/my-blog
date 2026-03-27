package com.xulei.myblogbackend.controller;

import com.xulei.myblogbackend.entity.LoginInfo;
import com.xulei.myblogbackend.entity.Music;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.excpetion.BaseException;
import com.xulei.myblogbackend.service.MusicService;
import com.xulei.myblogbackend.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 音乐控制器
 */
@Slf4j
@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    /**
     * 获取音乐列表（公开接口）
     */
    @GetMapping
    public Result<List<Music>> getMusicList() {
        List<Music> list = musicService.getMusicList();
        return Result.success(list);
    }

    /**
     * 上传音乐（仅xuleixulei）
     */
    @PostMapping("/upload")
    public Result<Music> uploadMusic(@RequestParam("musicName") String musicName,
                                     @RequestParam("musicUrl") String musicUrl,
                                     @RequestParam(value = "musicArtist", required = false) String musicArtist) {
        try {
            // 检查是否管理员
            if (!isAdmin()) {
                return Result.fail("无权操作");
            }

            Music music = musicService.uploadMusic(musicName, musicUrl, musicArtist);
            return Result.success(music);
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 删除音乐（仅xuleixulei）
     */
    @DeleteMapping("/{musicId}")
    public Result deleteMusic(@PathVariable String musicId) {
        try {
            // 检查是否管理员
            if (!isAdmin()) {
                return Result.fail("无权操作");
            }

            musicService.deleteMusic(musicId);
            return Result.ok("删除成功");
        } catch (BaseException e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 获取音乐数量（公开接口）
     */
    @GetMapping("/count")
    public Result<Long> getMusicCount() {
        long count = musicService.getMusicCount();
        return Result.success(count);
    }

    /**
     * 检查当前用户是否管理员
     */
    private boolean isAdmin() {
        LoginInfo user = UserHolder.getUser();
        return user != null && "xuleixulei".equals(user.getUserName());
    }
}
