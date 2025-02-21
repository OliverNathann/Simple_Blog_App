package com.blog.Simple_Blog.controller;

import com.blog.Simple_Blog.model.Post;
import com.blog.Simple_Blog.model.Status;
import com.blog.Simple_Blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create Post untuk USER -> DRAFT dan ADMIN -> ANY
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post, Authentication authentication) {
        String role = getUserRole(authentication);
        String username = authentication.getName();

        if ("ROLE_USER".equals(role) && post.getStatus() == Status.PUBLISH) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Users can only create drafts.");
        }
        post.setAuthor(username);
        return ResponseEntity.ok(postService.savePost(post));
    }

    // Get All Posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // Get Post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Post Hanya Admin yang bisa Publish, Users hanya bisa edit drafnya sendiri
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post updatedPost, Authentication authentication) {
        Optional<Post> existingPostOpt = postService.getPostById(id);

        if (existingPostOpt.isPresent()) {
            Post existingPost = existingPostOpt.get();
            String role = getUserRole(authentication);
            String username = authentication.getName();

            if (existingPost.getAuthor() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This post has no author set. Contact admin.");
            }

            if ("ROLE_USER".equals(role)) {
                if (!existingPost.getAuthor().equals(username)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Users can only edit their own posts.");
                }
                if (updatedPost.getStatus() == Status.PUBLISH) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Users cannot publish posts.");
                }
            }

            if (updatedPost.getTitle() != null) existingPost.setTitle(updatedPost.getTitle());
            if (updatedPost.getContent() != null) existingPost.setContent(updatedPost.getContent());
            if (updatedPost.getStatus() != null) existingPost.setStatus(updatedPost.getStatus());
            if (updatedPost.getPublishDate() != null) existingPost.setPublishDate(updatedPost.getPublishDate());
            if (updatedPost.getTags() != null && !updatedPost.getTags().isEmpty()) {
                existingPost.setTags(updatedPost.getTags());
            }

            return ResponseEntity.ok(postService.savePost(existingPost));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete Post (Only Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        String role = getUserRole(authentication);

        if (!"ROLE_ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (postService.getPostById(id).isPresent()) {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Search Posts by Tag
    @GetMapping("/search")
    public ResponseEntity<List<Post>> getPostsByTag(@RequestParam String tag) {
        List<Post> posts = postService.getPostsByTag(tag);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }

    // Helper method to get User Role
    private String getUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_GUEST");
    }
}
