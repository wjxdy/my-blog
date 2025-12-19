package com.xulei.myblogbackend.interceptor;

import com.xulei.myblogbackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class Logininterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 注意：WebConfig中配置的excludePathPatterns会让拦截器跳过这些路径，不执行此方法
        // 因此能执行到这里的请求，都是需要验证Token的路径
        String token = request.getHeader("token");
        String requestPath = request.getRequestURI(); // 获取实际请求路径
        if (token == null || !JwtUtils.verify(token)) {
            log.info("拦截器拦截请求: {}", requestPath);
            response.setStatus(401);
            return false;
        }
        log.info("Token验证通过，放行请求: {}", requestPath);
        return true;
    }
}
