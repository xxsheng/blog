package com.spades.blog.dao;

import com.spades.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category,String> {
    Category findByName(String name);
}
