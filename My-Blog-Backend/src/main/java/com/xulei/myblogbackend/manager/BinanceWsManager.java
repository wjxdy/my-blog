package com.xulei.myblogbackend.manager;

import com.xulei.myblogbackend.service.BinanceKlineHistoryService;
import com.xulei.myblogbackend.utils.BinanceWebSocketClient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Slf4j
public class BinanceWsManager {

    private final StringRedisTemplate redisTemplate;
    volatile private BinanceWebSocketClient client;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private BinanceKlineHistoryService binanceKlineHistoryService;


    public BinanceWsManager(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void start() {
        connect();
    }


    public void connect() {
        try {
            client = new BinanceWebSocketClient(redisTemplate,simpMessagingTemplate, this,binanceKlineHistoryService);
            client.connect();
            log.info("Binance WebSocket connecting...");
        } catch (Exception e) {
            log.error("创建 WebSocketClient 失败", e);
            scheduleReconnect();
        }
    }

    public void scheduleReconnect() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.warn("重连 Binance WebSocket...");
                connect();
            }
        }, 3000);
    }

    public boolean isConnected() {
        return client != null && client.isOpen();
    }

    public void stop() {
        if (client != null) {
            client.stopClient();
        }
    }
}
