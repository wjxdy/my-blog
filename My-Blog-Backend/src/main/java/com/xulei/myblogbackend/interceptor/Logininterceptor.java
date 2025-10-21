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

        String token = request.getHeader("token");


        if (token==null||!JwtUtils.verify(token)){
            log.info("拦截器拦截成功{}",request.getPathInfo());
            response.setStatus(401);

            return false;
        }
        return true;
    }
}
