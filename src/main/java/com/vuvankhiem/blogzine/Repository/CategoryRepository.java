package com.vuvankhiem.blogzine.Repository;

import com.vuvankhiem.blogzine.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAll();

    Category findByCategoryName(String categoryName);



}