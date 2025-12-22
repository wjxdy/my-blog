package com.xulei.myblogbackend.webSocketService;

import com.xulei.myblogbackend.manager.BinanceWsManager;
import com.xulei.myblogbackend.utils.BinanceWebSocketClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.net.URISyntaxException;

/**
 * @author xulei
 */
@Slf4j
@Service
public class BinanceWebSocketService {

    private final BinanceWsManager manager;

    public BinanceWebSocketService(BinanceWsManager manager) {
        this.manager = manager;
    }

    public boolean isConnected() {
        return manager.isConnected();
    }

    public void stop() {
        manager.stop();
    }
}
