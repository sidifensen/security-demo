package com.atguigu.securitydemo.controller;

import com.atguigu.securitydemo.entity.User;
import com.atguigu.securitydemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @GetMapping("/list")
    //默认情况下所有用户都可以访问该接口,加了@PreAuthorize注解后，只有拥有USER角色的用户才可以访问
    //@PreAuthorize里面可以写表达式，比如hasRole('ADMIN')表示用户必须有ADMIN角色，authentication.name = 'admin'表示用户必须是admin
    @PreAuthorize("hasRole('ADMIN') and authentication.name == 'admin' ")//在授权之前，先判断是否有USER角色
    public List<User> getList(){
        return userService.list();
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAuthority('USER_ADD')")//hasAuthority()方法可以用来判断用户是否有某个权限，比如这里判断用户是否有USER_ADD权限
    public void add(@RequestBody User user){
        userService.saveUserDetails(user);
    }
}




































