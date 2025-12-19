package com.xulei.myblogbackend.interceptor;

import com.xulei.myblogbackend.entity.LoginInfo;
import com.xulei.myblogbackend.utils.JwtUtils;
import com.xulei.myblogbackend.utils.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
public class Prefixinterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        // 1. 无Token直接放行（交给Logininterceptor处理）
        if (token == null || token.isEmpty()) {
            return true;
        }
        // 2. 有Token时解析，但增加异常处理，避免解析失败中断请求
        try {
            // 先验证Token有效性（与Logininterceptor保持一致）
            if (!JwtUtils.verify(token)) {
                log.warn("Token无效，解析失败: {}", token);
                return true; // 不拦截，交给Logininterceptor返回401
            }
            // Token有效且用户信息未保存时，解析并保存
            if (UserHolder.getUser() == null) {
                Map<String, Object> map = JwtUtils.parseJwt(token);
                log.info("登录信息解析成功: {}", map);

                // 安全获取参数，避免NullPointerException
                String userName = map.get("userName") != null ? map.get("userName").toString() : null;
                Long userId = map.get("userId") != null ? ((Number) map.get("userId")).longValue() : null;
                Long exp = map.get("exp") != null ? ((Number) map.get("exp")).longValue() : null;

                if (userName == null || userId == null) {
                    log.warn("Token解析的用户信息不完整");
                    return true;
                }
                LoginInfo info = new LoginInfo();
                info.setUserName(userName);
                info.setUserId(userId);
                info.setExp(exp);
                UserHolder.saveUser(info);
                log.info("用户信息保存成功: {}", info);
            }
        } catch (Exception e) {
            log.error("Token解析异常", e);
            // 解析失败不拦截，交给Logininterceptor处理
            return true;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    // 无论请求成功/失败，必然执行，彻底清理
        UserHolder.removeUser();
    }
}
