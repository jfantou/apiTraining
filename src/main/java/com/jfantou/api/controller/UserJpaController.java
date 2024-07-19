package com.jfantou.api.controller;

import com.jfantou.api.exception.UserNotFoundException;
import com.jfantou.api.model.Post;
import com.jfantou.api.model.User;
import com.jfantou.api.repository.PostRepository;
import com.jfantou.api.repository.UserRepository;
import com.jfantou.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaController {

    private UserService userService;
    private UserRepository userRepository;
    private PostRepository postRepository;

    UserJpaController(UserService userService, UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/jpa/users")
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/jpa/users/{id}")
    public EntityModel<User> getUser(@PathVariable(name = "id") Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User with id: " + id +" not found");
        } else {
            EntityModel<User> entityModel = EntityModel.of(user.get());
            WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).allUsers());
            entityModel.add(webMvcLinkBuilder.withRel("all-users"));
            return entityModel;
        }
    }

    @PostMapping(value = "/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        Integer id = userRepository.save(user).getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/jpa/users/{id}")
    public void deleteUser(@PathVariable(name = "id") Integer id){
        userRepository.deleteById(id);
    }

    @GetMapping(value = "/jpa/users/{id}/posts")
    public List<Post> allPostForUser(@PathVariable(name = "id") Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User with id: " + id +" not found");
        }

        return user.get().getPostList();
    }

    @PostMapping(value = "/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@Valid @RequestBody Post post, @PathVariable(name = "id") Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User with id: " + id +" not found");
        }
        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
