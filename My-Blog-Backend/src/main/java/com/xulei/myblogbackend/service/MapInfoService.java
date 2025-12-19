package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.entity.MapInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulei
 * @since 2025-12-06
 */
public interface MapInfoService extends IService<MapInfo> {

    void saveInfo(MapInfo info);
}
