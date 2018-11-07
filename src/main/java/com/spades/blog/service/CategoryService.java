package com.spades.blog.service;

import com.spades.blog.dao.CategoryDao;
import com.spades.blog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public List<Category> list(){
        return categoryDao.findAll();
    }

    public Category findById(String id){
        return categoryDao.getOne(id);
    }

    public Category findByName(String name){
        return  categoryDao.findByName(name);
    }

}
