package com.xulei.myblogbackend.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class IpUtils {

    /**
     * 获取客户端真实 IP 地址（无代理场景直接用，有代理需配合下方配置）
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        // 1. 优先从代理相关请求头获取（如果有代理但未配置转发，这里可能为空）
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // WebLogic 代理
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        // 2. 无代理时，从 TCP 连接中获取真实 IP（核心方法）
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr(); // 底层获取的是 TCP 连接的客户端 IP
        }

        // 3. 处理多代理场景（X-Forwarded-For 可能包含多个 IP，用逗号分隔，第一个是真实 IP）
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim(); // 取第一个非 unknown 的 IP
        }

        // 4. 特殊情况：本地测试时可能返回 0:0:0:0:0:0:0:1（IPv6），转为 IPv4 的 127.0.0.1
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
