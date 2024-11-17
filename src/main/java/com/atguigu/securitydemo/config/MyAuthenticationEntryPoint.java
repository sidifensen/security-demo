package com.atguigu.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;

//entrypoint 进入点，当未登录时，会调用此类中的commence方法，返回json数据到前端
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String localizedMessage = "需要登录";//authException.getLocalizedMessage();

        //自定义返回数据
        HashMap result = new HashMap<>();
        result.put("code",-1);//失败
        result.put("message",localizedMessage);

        //将结果转换为json字符串
        String json = JSON.toJSONString(result);

        //返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");//响应头
        response.getWriter().println(json);

    }
}











































