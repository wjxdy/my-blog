package com.xulei.myblogbackend.utils;

/**
 * @author xulei
 */
public class StringContext {

    public static final String REDIS_EMAIL_SEND_KEY =  "email:send:";
    public static final String EMAIL_VERIFIED_FLAG =  "1";
    public static final long EMAIL_VERIFIED_EXPIRE =  60*10;
    public static final long PREFIX_EMAIL_VERIFIED_EXPIRE =  60;


    //binance websocket
    public static final String BINANCE_WEBSOCKET_URL = "wss://stream.binance.com:9443/ws/btcusdt@kline_1m";
    // 重连延迟（3秒）
    public static final long RECONNECT_DELAY = 3000;


    //Kline Redis
    // 阈值：超过200条触发入库
    public static final int ZSET_THRESHOLD = 200;
    // 每次入库数量：最后100条
    public static final int BATCH_INSERT_COUNT = 100;
    //kline cache key
    public static final String KLINE_CHCHE_KEY = "kline:BTCUSDT:1m";

}
