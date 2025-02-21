package com.blog.Simple_Blog.service;

import com.blog.Simple_Blog.model.Post;
import com.blog.Simple_Blog.model.Status;
import com.blog.Simple_Blog.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Get All Posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get Post by ID
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // Save or Update Post
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    // Delete Post
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // Search Posts by Tag
    public List<Post> getPostsByTag(String tag) {
        return postRepository.findByTags_Label(tag);
    }
}
