package com.atguigu.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        //自定义返回数据
        HashMap result = new HashMap<>();
        result.put("code",-1);
        result.put("message","没有权限");

        //将结果转换为json字符串
        String json = JSON.toJSONString(result);

        //返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");//响应头
        response.getWriter().println(json);

    }
}













