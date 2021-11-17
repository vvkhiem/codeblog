package com.vuvankhiem.blogzine.Repository;

import com.vuvankhiem.blogzine.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findAll();

    Tag findByTagName(String tagName);


}