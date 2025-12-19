package com.xulei.myblogbackend.controller;

import com.xulei.myblogbackend.entity.MapInfo;
import com.xulei.myblogbackend.entity.Result;
import com.xulei.myblogbackend.service.MapInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulei
 * @since 2025-12-06
 */
@Slf4j
@RestController
@RequestMapping("/news")
public class MapInfoController {

    @Autowired
    private MapInfoService mapInfoService;


    @PostMapping
    public Result saveInfo(@RequestBody MapInfo info){
        try {
            mapInfoService.saveInfo(info);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Result.ok();
    }


}
