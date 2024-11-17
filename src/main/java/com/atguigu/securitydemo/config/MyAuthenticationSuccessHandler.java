package com.atguigu.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import com.github.yulichang.wrapper.resultmap.IResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();//获取登录用户信息
//        Object credentials = authentication.getCredentials();//获取登录用户密码(凭证)
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//获取用户权限信息

        //自定义返回数据
        HashMap result = new HashMap<>();
        result.put("code",0);//成功
        result.put("message","登录成功");
        result.put("data",principal);//用户信息

        //将结果转换为json字符串
        String json = JSON.toJSONString(result);

        //返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");//响应头
        response.getWriter().println(json);

    }
}
















































