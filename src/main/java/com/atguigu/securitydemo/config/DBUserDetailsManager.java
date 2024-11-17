package com.atguigu.securitydemo.config;

import com.atguigu.securitydemo.entity.User;
import com.atguigu.securitydemo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsService {

    @Resource
    private UserMapper userMapper;

    /**
     * 西那个数据库中插入新的用户信息
     * @param userDetails
     */
    @Override
    public void createUser(UserDetails userDetails) {

        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        userMapper.insert(user);

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    /**
     * 从数据库中查询用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);

        User user = userMapper.selectOne(userQueryWrapper);


        //给用户授权
//        if(user == null){
//            throw new UsernameNotFoundException(username);
//        } else {
//
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(() -> "USER_LIST");//给用户授权
//            authorities.add(() -> "USER_ADD");//给用户授权
//
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    user.getEnabled(),
//                    true,//账户是否未过期
//                    true,//用户凭证是否未过期
//                    true,//用户是否未被锁定
//                    authorities//用户的权限集合
//            );
//        }

        //对用户角色的授权
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.getEnabled())//账户是否禁用
                .credentialsExpired(false)//账户是否过期
                .accountLocked(false)//账户是否锁定
                .roles("ADMIN")//给用户添加角色     "authority":"ROLE_USER"
                .authorities("USER_ADD","USER_LIST")//会覆盖角色，给用户添加权限
                .build();

    }
}


























