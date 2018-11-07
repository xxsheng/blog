package com.spades.blog.service;

import com.spades.blog.dao.UserDao;
import com.spades.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServie {
    @Autowired
    private UserDao userDao;

    public boolean login(String username,String password){
        User user = userDao.findByUserNameAndPassword(username,password);
        if (null == user) {
            return false;
        }else {
            return true;
        }
    }
}
