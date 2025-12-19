package com.xulei.myblogbackend.utils;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
@Slf4j
public class BinanceWebSocketClient extends WebSocketClient {

    // 重连相关配置
    private static final long RECONNECT_DELAY = 3000; // 重连延迟（3秒）
    private Timer reconnectTimer; // 重连定时器
    private boolean isManualStop = false; // 是否手动停止（避免重连）

    // 构造方法：初始化连接地址
    public BinanceWebSocketClient() throws URISyntaxException {
        super(new URI("wss://stream.binance.com:9443/ws/btcusdt@kline_12h"));
        initReconnectTimer(); // 初始化重连定时器
    }

    // 初始化重连定时器（连接断开时自动重试）
    private void initReconnectTimer() {
        reconnectTimer = new Timer("WebSocket-Reconnect-Timer", true);
    }

    // -------------------------- 核心回调方法 --------------------------
    /**
     * 连接建立成功时触发
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info("WebSocket 连接成功！状态码：" + handshakedata.getHttpStatus());
        log.info("开始接收 BTC/USDT 实时成交数据...\n");
        // 取消重连（连接成功后无需重试）
        reconnectTimer.cancel();
    }



    /**
     * 接收服务器推送的数据时触发（核心：解析行情数据）
     */
    @Override
    public void onMessage(String message) {
        /*// 解析 JSON 数据（Binance 逐笔成交数据格式）
        JSONObject data = JSONObject.parseObject(message);

        // 提取核心字段（时间、币对、价格、数量、成交方向）
        long timestamp = data.getLongValue("T"); // 成交时间戳（毫秒）
        String symbol = data.getString("s"); // 币对（如 BTCUSDT）
        String price = data.getString("p"); // 成交价格（USDT）
        String quantity = data.getString("q"); // 成交数量（BTC）
        boolean isSell = data.getBooleanValue("m"); // 是否为卖单主动成交（true=卖，false=买）

        // 格式化输出
        String tradeType = isSell ? "卖单成交" : "买单成交";
        String timeStr = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new java.util.Date(timestamp));
        System.out.printf(
                "时间：%s | 币对：%s | 价格：%s USDT | 数量：%s BTC | 类型：%s%n",
                timeStr, symbol, price, quantity, tradeType
        );*/
        log.info("交易信息 {}",message);
    }

    /**
     * 连接关闭时触发
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.printf(
                "\nWebSocket 连接关闭！状态码：%d，原因：%s，是否服务器主动关闭：%s%n",
                code, reason, remote
        );

        // 若不是手动停止，触发自动重连
        if (!isManualStop) {
            System.out.println("准备重新连接...");
            reconnectTimer = new Timer("WebSocket-Reconnect-Timer", true);
            reconnectTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    connect(); // 重新发起连接
                }
            }, RECONNECT_DELAY);
        }
    }

    /**
     * 连接异常时触发（如网络中断、服务器报错）
     */
    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket 异常：" + ex.getMessage());
        ex.printStackTrace();
        // 异常时主动关闭连接（触发 onClose 重连）
        if (isOpen()) {
            close(1008, "连接异常，主动关闭");
        }
    }

    // -------------------------- 主动控制方法 --------------------------
    /**
     * 手动停止 WebSocket 连接（核心：调用 close() 方法）
     */
    public void stopClient() {
        isManualStop = true; // 标记为手动停止（禁止重连）
        if (isOpen()) {
            System.out.println("\n正在停止 WebSocket 连接...");
            // 调用 close() 关闭连接，指定状态码和原因（1000=正常关闭）
            close(1000, "用户主动停止接收行情数据");
        } else {
            System.out.println("WebSocket 已处于关闭状态，无需重复停止");
        }
    }


}
