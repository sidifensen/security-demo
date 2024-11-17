package com.atguigu.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration//配置类
//@EnableWebSecurity//开启springsecurity的自定义(在spirngboot中默认开启了)
@EnableMethodSecurity//开启方法授权
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //开启授权保护
        http.authorizeHttpRequests(
                authorize -> authorize//基于request的授权配置
//                        .requestMatchers("/user/list").hasAuthority("USER_LIST")//配置授权规则 用户-权限-资源
//                        .requestMatchers("/user/add").hasAuthority("USER_ADD")//配置授权规则 用户-权限-资源
//                        .requestMatchers("/user/**").hasRole("ADMIN")//配置授权规则 用户-角色-资源

                        //对所有请求开启授权保护
                        .anyRequest()
                        //已认证的请求会被自动授权
                        .authenticated()
                );

        //配置登录页面
        http.formLogin(form ->{
            form.loginPage("/login").permitAll()//无需授权即可访问登录页面
                    .successHandler(new MyAuthenticationSuccessHandler())//认证成功时的处理
                    .failureHandler(new MyAuthenticationFailureHandler());//认证失败时的处理
//                    .usernameParameter("username")//配置自定义的表单用户名参数,默认是username
//                    .passwordParameter("password")//配置自定义的表单密码参数,默认是password
//                    .failureUrl("/login?error")//登录失败跳转到登录页面并显示错误信息,默认是error
        });//使用表单授权方式
//        .formLogin(withDefaults());//使用表单授权方式
//        .httpBasic(withDefaults());//使用基本授权方式

        //配置注销页面
        http.logout(logout ->
                logout.logoutSuccessHandler(new MyLogoutSuccessHandler()));//注销成功的处理

        //配置异常处理
        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(new MyAuthenticationEntryPoint());//请求需要认证时,未认证的处理

            //写法1
//            exception.accessDeniedHandler(new MyAccessDeniedHandler());//请求被拒绝时的处理,即用户没有权限访问资源

            //写法2
//            exception.accessDeniedHandler(new AccessDeniedHandler() {
//                @Override
//                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                    //自定义返回数据
//                    HashMap result = new HashMap<>();
//                    result.put("code",-1);
//                    result.put("message","没有权限");
//
//                    //将结果转换为json字符串
//                    String json = JSON.toJSONString(result);
//
//                    //返回json数据到前端
//                    response.setContentType("application/json;charset=UTF-8");//响应头
//                    response.getWriter().println(json);
//                }
//            });

            //写法3
            exception.accessDeniedHandler((request, response, accessDeniedException)->{
                //自定义返回数据
                HashMap result = new HashMap<>();
                result.put("code",-1);
                result.put("message","没有权限");

                //将结果转换为json字符串
                String json = JSON.toJSONString(result);

                //返回json数据到前端
                response.setContentType("application/json;charset=UTF-8");//响应头
                response.getWriter().println(json);

            });
        });



        //跨域
        http.cors(withDefaults());


        //配置会话信息过期策略
        http.sessionManagement(session ->
                session.maximumSessions(1).expiredSessionStrategy(new MySessionInformationExpiredStrategy()));//同一用户在线最大数量

        //禁用CSRF防护
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
////
//    @Bean
//    public UserDetailsService userDetailsService() {
//        //创建基于内存的用户信息管理器
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//       //使用manager管理UserDetails对象
//        manager.createUser(
//                //创建UserDetails对象用于管理用户名和用户密码和用户角色,用户权限
//                User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build()
//        );
//        return manager;
//    }

//    @Bean //直接在DBUserDetailsManager上加@Component即可实现该功能
//    public UserDetailsService userDetailsService() {
//        //创建基于数据库的用户信息管理器
//        DBUserDetailsManager manager = new DBUserDetailsManager();
//        return manager;
//    }
}

























