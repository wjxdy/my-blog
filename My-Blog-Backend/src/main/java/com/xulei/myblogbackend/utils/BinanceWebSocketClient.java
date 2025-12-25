package com.xulei.myblogbackend.utils;

import com.alibaba.fastjson2.JSON;
import com.xulei.myblogbackend.entity.BinanceKlineEvent;
import com.xulei.myblogbackend.entity.Kline;
import com.xulei.myblogbackend.manager.BinanceWsManager;
import com.xulei.myblogbackend.service.BinanceKlineHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import static com.xulei.myblogbackend.utils.StringContext.*;

/**
 * @author xulei
 */
@Slf4j
@Component
public class BinanceWebSocketClient extends WebSocketClient {

    private final StringRedisTemplate stringRedisTemplate;
    private final BinanceWsManager manager;
    private final SimpMessagingTemplate messagingTemplate;
    private final ZSetKlineCacheUtil zSetKlineCacheUtil;
    private final BinanceKlineHistoryService binanceKlineHistoryService;



    public BinanceWebSocketClient(
            StringRedisTemplate stringRedisTemplate,
            SimpMessagingTemplate messagingTemplate,
            BinanceWsManager manager,
            BinanceKlineHistoryService binanceKlineHistoryService
    ) throws URISyntaxException {
        super(new URI(BINANCE_WEBSOCKET_URL));
        this.stringRedisTemplate = stringRedisTemplate;
        this.messagingTemplate = messagingTemplate;
        this.manager = manager;
        this.binanceKlineHistoryService = binanceKlineHistoryService;
        this.zSetKlineCacheUtil = new ZSetKlineCacheUtil(stringRedisTemplate,binanceKlineHistoryService);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info("WebSocket websocket connected");
    }

    @Override
    public void onMessage(String message) {
        try {
            BinanceKlineEvent event = JSON.parseObject(message, BinanceKlineEvent.class);
            // 2. 先做空判断，避免空指针（优先判断非空，再做业务逻辑）
            if (event == null || event.getK() == null) {
                log.warn("K线事件或K线数据为空，忽略处理：message={}", message);
                return;
            }
            // 3. 仅处理已闭合的K线（isX()为true表示K线结束）
            if (!event.getK().isX()) {
                return;
            }
            zSetKlineCacheUtil.checkAndHandleZSetData();
            // 秒级时间戳

            long score = Instant.now().getEpochSecond();;

            // 存 ZSet：key = kline:{symbol}:{interval}, score = 开盘时间, value = JSON
            String key = KLINE_CACHE_KEY;
            stringRedisTemplate.opsForZSet().add(key, message, score);
            // 设置 key 的过期时间，例如 1 天（86400 秒）
            stringRedisTemplate.expire(key, Duration.ofDays(1));
            // 前端订阅路径 /topic/kline/{symbol}/{interval}
            String topic = "/topic/kline/";
            messagingTemplate.convertAndSend(topic, event);
            log.info("广播 K 线数据给前端: topic={}", topic);
        } catch (Exception e) {
            log.error("解析或存储 K 线数据异常", e);
        }
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.warn("WebSocket close: code={}, reason={}, remote={}", code, reason, remote);
    }


    @Override
    public void onError(Exception ex) {
        log.error("WebSocket 异常", ex);
        if (isOpen()) {
            close(1008, "连接异常，主动关闭");
        }
    }


    public void stopClient() {
        if (isOpen()) {
            log.info("正在停止 WebSocket 连接...");
            close(1000, "用户主动停止接收 K 线数据");
        }
    }
}
