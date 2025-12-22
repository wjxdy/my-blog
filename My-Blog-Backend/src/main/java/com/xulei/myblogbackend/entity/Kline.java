package com.xulei.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kline {
    // K线起始时间
    private long t;
    // K线结束时间
    private long T;
    // 交易对
    private String s;
    // K线间隔
    private String i;
    // 第一笔成交ID
    private long f;
    // 最后一笔成交ID
    private long L;
    // 开盘价
    private String o;
    // 收盘价
    private String c;
    // 最高价
    private String h;
    // 最低价
    private String l;
    // 成交量
    private String v;
    // 成交笔数
    private long n;
    // 是否完结
    private boolean x;
    // 成交额
    private String q;
    // 主动买入的成交量
    private String V;
    // 主动买入的成交额
    private String Q;
    // 忽略字段
    private String B;
}
