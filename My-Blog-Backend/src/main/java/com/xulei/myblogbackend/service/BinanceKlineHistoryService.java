package com.xulei.myblogbackend.service;

import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import com.xulei.myblogbackend.entity.BinanceKlineHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 币安K线历史数据表（适配WebSocket事件结构） 服务类
 * </p>
 *
 * @author xulei
 * @since 2025-12-23
 */
public interface BinanceKlineHistoryService extends IService<BinanceKlineHistory> {

    void addKlineHistoryCache(List<BinanceKlineEvent> list);

}
