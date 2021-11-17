package com.vuvankhiem.blogzine.Repository;

import com.vuvankhiem.blogzine.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    long countByPost_PostID(int postID);

    @Query("select c from Comment c left join fetch c.account group by c.account.accountID order by count (c.account.accountID) desc ")
    List<Comment> findByGroupByAccount();

    @Query("select c from Comment c left join fetch c.post where c.account.accountID = ?1 group by c.post.postID order by c.post.postID asc ")
    List<Comment> findByAccount_AccountIDAndGroupByPostID(int accountID);

    List<Comment> findByAccount_AccountIDAndPost_PostID(int accountID, int postID);

    void deleteByCommentID(int commentID);

    @Override
    void deleteAll();
}