package com.example.mockband.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //依赖注入通用的用户服务类
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private LoginSuccessHandle loginSuccessHandle;

    @Bean
    //必须配置密码加密类型，这里选择不加密
    public static NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置登录验证和密码加密类型
        auth.userDetailsService(userSecurityService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //这个配置是有优先级的，最先配置的东西优先级最高
        http
                .csrf().disable()//跨域请求防御一定要关闭，否则注册和登录的post根本就提不上来。
                    .authorizeRequests()
                    .antMatchers("/css/**","/js/**","/images/**","/lib/**","/static/**","/login").permitAll()  //这里面的路径可以直接访问。静态文件路径一定要添加，否则网页没有样式
                    .anyRequest().authenticated()  //其他路径需要登录后才能访问
                .and()
                    .formLogin()
                    .loginPage("/login")  //设置默认登录页面(这里重定向到登录控制器，实现权限控制)
                    .usernameParameter("username").passwordParameter("password")//匹配前端的用户名密码参数名称
                    .successHandler(loginSuccessHandle)//登录成功处理
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")  //设置登出后的跳转页面
                .and()
                .headers().frameOptions().sameOrigin();// 允许来自同一来源的H2 控制台的请求
//                .and()
//                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false);

    }
}
