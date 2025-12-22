package com.xulei.myblogbackend.handler;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BinanceWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    public BinanceWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void onNewCandle(JSONObject kline) {
        // 推送给前端订阅 /topic/kline
        messagingTemplate.convertAndSend("/topic/kline", kline);
    }
}
