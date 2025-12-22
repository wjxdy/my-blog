/*
package com.xulei.myblogbackend.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;
import java.util.Scanner;

@SpringBootTest
public class WebSocketTest {

    @Test
    public void testDemo(){

        // 1. Binance 现货 WebSocket 地址（BTC/USDT 逐笔成交频道）
        String wsUrl = "wss://stream.binance.com:9443/ws/btcusdt@trade";

        try {
            // 2. 创建并启动 WebSocket 客户端
            BinanceWebSocketClient client = new BinanceWebSocketClient();
            client.connect(); // 发起连接（异步操作，不会阻塞主线程）

            // 3. 监听命令行输入（输入 "stop" 停止连接）
            System.out.println("输入 'stop' 并回车，可停止接收数据...\n");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if ("stop".equalsIgnoreCase(input)) {
                    client.stopClient(); // 主动停止连接
                    scanner.close();
                    break;
                } else {
                    System.out.println("无效指令！请输入 'stop' 停止。");
                }
            }

            // 4. 等待连接关闭后退出程序
            while (client.isOpen()) {
                Thread.sleep(100);
            }
            System.out.println("程序已退出");

        } catch (URISyntaxException e) {
            System.err.println("WebSocket 地址格式错误：" + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("程序被中断：" + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


}
*/
