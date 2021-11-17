package com.vuvankhiem.blogzine.Repository;

import com.vuvankhiem.blogzine.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p left join fetch p.account")
    List<Post> findAll();

    @Query("select p from Post p left join fetch p.category left join fetch p.account order by function('RAND')")
    List<Post> getAllRandomPost();

    @Query("select p from Post p where p.postSlide=1")
    List<Post> getPostSildes();

    List<Post> findTop15ByOrderByPostViewsDesc();

    List<Post> findTop8ByOrderByPostIDDesc();

    long countAllBy();

    List<Post> findAllByCategory_CategoryNameOrderByPostIDDesc(String categoryName);

    long countByCategory_CategoryName(String categoryName);

    @Query("select p from Post p left join fetch p.category left join fetch p.account where p.postTitle like %:s% or p.postDescription like %:s% order by p.postID desc")
    List<Post> findByTxtSearchOrderByPostIDDesc(@Param("s") String txtSearch);

    Post findByPostID(int postID);

    List<Post> findTop4ByOrderByPostIDDesc();

    List<Post> findByOrderByPostIDDesc();

    List<Post> findByOrderByPostViewsDesc();

    List<Post> findByPostDate(String postDate);

    void deleteByPostID(int postID);

    List<Post> findByAccount_AccountIDAndComments_Post_PostIDIsNotNull(int accountID);


}