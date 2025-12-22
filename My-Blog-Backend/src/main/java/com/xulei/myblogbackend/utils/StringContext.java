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

}
