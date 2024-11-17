package com.atguigu.securitydemo.service;

import com.atguigu.securitydemo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    void saveUserDetails(User user);
}