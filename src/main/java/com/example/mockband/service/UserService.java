package com.example.mockband.service;

import com.example.mockband.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService{
    public String  findbyPictureId(Long id);
    public List<User> findAll();


}
