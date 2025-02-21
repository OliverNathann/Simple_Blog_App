package com.blog.Simple_Blog.repository;

import com.blog.Simple_Blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTags_Label(String tag);
}
