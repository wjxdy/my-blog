package com.xulei.myblogbackend.entity;

import lombok.Data;

/**
 * Binance K线实体类
 */
@Data
public class BinanceKlineEvent {
    // 事件类型
    private String e;
    // 事件时间（毫秒）
    private long E;
    // 交易对
    private String s;
    // K线详情
    private Kline k;




}
