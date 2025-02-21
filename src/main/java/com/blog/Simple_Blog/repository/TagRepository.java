package com.blog.Simple_Blog.repository;

import com.blog.Simple_Blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
