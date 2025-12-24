package com.xulei.myblogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 币安K线历史数据表（适配WebSocket事件结构）
 * </p>
 *
 * @author xulei
 * @since 2025-12-23
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("binance_kline_history")
public class BinanceKlineHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键，唯一标识每条K线记录
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事件类型，固定为kline
     */
    private String eventType;

    /**
     * 事件时间（毫秒级时间戳，对应JSON中的E）
     */
    private String eventTime;

    /**
     * 交易对（如BNBBTC，对应JSON中的s/k.s）
     */
    private String symbol;

    /**
     * K线起始时间（毫秒级时间戳，对应JSON中的k.t）
     */
    private String klineStartTime;

    /**
     * K线结束时间（毫秒级时间戳，对应JSON中的k.T）
     */
    private long klineEndTime;

    /**
     * K线间隔（如1m/5m/1h，对应JSON中的k.i）
     */
    private String intervalType;

    /**
     * K线期间第一笔成交ID（对应JSON中的k.f）
     */
    private String firstTradeId;

    /**
     * K线期间最后一笔成交ID（对应JSON中的k.L）
     */
    private String lastTradeId;

    /**
     * 开盘价（K线第一笔成交价，对应JSON中的k.o）
     */
    private BigDecimal openPrice;

    /**
     * 收盘价（K线最后一笔成交价，对应JSON中的k.c）
     */
    private BigDecimal closePrice;

    /**
     * 最高价（K线期间最高成交价，对应JSON中的k.h）
     */
    private BigDecimal highPrice;

    /**
     * 最低价（K线期间最低成交价，对应JSON中的k.l）
     */
    private BigDecimal lowPrice;

    /**
     * K线期间成交量（对应JSON中的k.v）
     */
    private BigDecimal volume;

    /**
     * K线期间成交笔数（对应JSON中的k.n）
     */
    private Integer tradeCount;

    /**
     * K线是否完结（0=未完结，1=已完结，对应JSON中的k.x）
     */
    private Boolean isKlineClosed;

    /**
     * K线期间成交额（对应JSON中的k.q）
     */
    private BigDecimal quoteVolume;

    /**
     * 主动买入成交量（对应JSON中的k.V）
     */
    private BigDecimal buyVolume;

    /**
     * 主动买入成交额（对应JSON中的k.Q）
     */
    private BigDecimal buyQuoteVolume;

    /**
     * 忽略参数（对应JSON中的k.B，预留字段）
     */
    private String ignoreParam;

    /**
     * 记录入库时间
     */
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    private LocalDateTime updateTime;
}
