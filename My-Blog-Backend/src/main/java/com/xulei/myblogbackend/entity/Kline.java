package com.xulei.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kline {
    private String t;  // K线起始时间
    private long T;  // K线结束时间
    private String s; // 交易对
    private String i; // K线间隔
    private String f;   // 第一笔成交ID
    private String L;   // 最后一笔成交ID
    private String o; // 开盘价
    private String c; // 收盘价
    private String h; // 最高价
    private String l; // 最低价
    private String v; // 成交量
    private long n;   // 成交笔数
    private boolean x; // 是否完结
    private String q; // 成交额
    private String V; // 主动买入成交量
    private String Q; // 主动买入成交额
    private String B; // 忽略字段

    // 为 Service 映射提供语义化 getter
    public String getLastTradeId() { return L; }
    public String getLowPrice() { return l; }

    public long getEndTime(){return T; }
    public String getStartTime(){return t; }
}
