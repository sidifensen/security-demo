package com.atguigu.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        //自定义返回数据
        HashMap result = new HashMap<>();
        result.put("code",-1);//注销成功
        result.put("message","注销成功");

        //将结果转换为json字符串
        String json = JSON.toJSONString(result);

        //返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");//响应头
        response.getWriter().println(json);

    }
}
