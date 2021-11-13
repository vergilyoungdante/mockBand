package com.example.mockband.controller;


import com.example.mockband.entity.User;
import com.example.mockband.entity.UserInfo;
import com.example.mockband.entity.Windows;
import com.example.mockband.repository.WindowsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class WindowsController {

    @Autowired
    WindowsRepository windowsRepository;

    @RequestMapping("/windows")
    public String identity(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Windows windows = windowsRepository.findWindowsByAuth(user.getAccountType());

        return windows.getValue();
    }
}
