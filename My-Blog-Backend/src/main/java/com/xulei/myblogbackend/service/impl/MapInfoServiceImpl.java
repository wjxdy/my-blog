package com.xulei.myblogbackend.service.impl;

import com.xulei.myblogbackend.entity.MapInfo;
import com.xulei.myblogbackend.mapper.MapInfoMapper;
import com.xulei.myblogbackend.service.MapInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulei
 * @since 2025-12-06
 */
@Service
public class MapInfoServiceImpl extends ServiceImpl<MapInfoMapper, MapInfo> implements MapInfoService {

    @Override
    public void saveInfo(MapInfo info) {

        try {
            info.setCreateTime(LocalDateTime.now());
            this.save(info);
        } catch (Exception e) {

        }

    }
}
