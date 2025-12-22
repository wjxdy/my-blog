package com.xulei.myblogbackend.config;//package com.itheima.config;

import com.xulei.myblogbackend.interceptor.Logininterceptor;
import com.xulei.myblogbackend.interceptor.Prefixinterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Logininterceptor logininterceptor;
    @Autowired
    private Prefixinterceptor prefixinterceptor;


    // 当前WebConfig配置正确，excludePathPatterns会让拦截器跳过这些路径，不执行preHandle方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logininterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(    // 以下路径不执行Logininterceptor的preHandle方法
                        "/user/login",   // 登录接口
                        "/user/register",   // 登录接口
                        "/article/list",
                        "/article/{id}",
                        "/article/getLog",
                        "/tag/**",
                        "/email/**",
                        "/binance/**",
                        "/news"
                ).order(1);

        registry.addInterceptor(prefixinterceptor)
                .addPathPatterns("/**")
                .order(0);  // 先执行，但已增加异常处理
    }


}
