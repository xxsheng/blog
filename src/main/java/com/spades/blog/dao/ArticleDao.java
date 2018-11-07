package com.spades.blog.dao;

import com.spades.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleDao extends JpaRepository<Article,String> {
    List<Article> findAllByCategory_Name(String name);

    @Query("from Article  where title like %:title%")
    List<Article> findByTitleLike(@Param("title")String title);


}
