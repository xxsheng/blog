package com.spades.blog.service;

import com.spades.blog.dao.ArticleDao;
import com.spades.blog.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Article getById(String id){
        Article article = articleDao.getOne(id);
        return article;
    }

    public List<Article> list(){
        return articleDao.findAll();
    }

    public List<Article> getArticleByCategoryName(String categoryName){
        return articleDao.findAllByCategory_Name(categoryName);
    }

    public void delete(Article article){
        articleDao.delete(article);
    }

    public void save(Article article){
        articleDao.save(article);
    }

    public List<Article> search(String key){
        return articleDao.findByTitleLike(key);
    }
}
