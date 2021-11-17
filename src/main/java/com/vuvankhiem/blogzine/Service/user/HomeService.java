package com.vuvankhiem.blogzine.Service.user;

import com.vuvankhiem.blogzine.DTO.CategoryDTO;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Entity.Subscriber;
import com.vuvankhiem.blogzine.Entity.Tag;

import java.util.List;

public interface HomeService {

    List<Post> getAllRandomPost();

    List<PostDTO> getPostByPage(List<Post> posts, int index, int quantity);

    List<Post> getPostSildes();

    List<PostDTO> getTop15PostMostViewed();

    List<PostDTO> getTop8PostLastest();

    void saveSubscriber(Subscriber subscriber);

    boolean existsSubscriberBySubscriberEmail(String email);

    List<CategoryDTO> getAllCategories();

    List<Tag> getAllTag();

    List<PostDTO> getTop4PostLastest();

}
