package com.xulei.myblogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import com.xulei.myblogbackend.entity.BinanceKlineHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 币安K线历史数据表（适配WebSocket事件结构） Mapper 接口
 * </p>
 *
 * @author xulei
 * @since 2025-12-23
 */
public interface BinanceKlineHistoryMapper extends BaseMapper<BinanceKlineHistory> {

    void addKlineHistoryCache(@Param("list") List<BinanceKlineEvent> list);
}
