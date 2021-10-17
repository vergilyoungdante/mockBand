package com.example.mockband.security;

import com.example.mockband.entity.User;
import com.example.mockband.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepository UserRepository;
    /**
     * 通过重写loadUserByUsername方法查询对应的用户
     * UserDetails是Spring Security的一个核心接口
     * UserDetails定义了可以获取用户名、密码、权限等与认证相关信息的方法
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名（页面接收的用户名）查询当前用户
        User user = UserRepository.findByName(username);
        //没有用户直接退出
        if(user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
