package com.jfantou.api.controller;

import com.jfantou.api.exception.UserNotFoundException;
import com.jfantou.api.model.User;
import com.jfantou.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {

    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public List<User> allUsers(){
        return userService.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public EntityModel<User> getUser(@PathVariable(name = "id") Integer id){
        User user = userService.getUserById(id);
        if(user == null){
            throw new UserNotFoundException("User with id: " + id +" not found");
        } else {
            EntityModel<User> entityModel = EntityModel.of(user);
            WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).allUsers());
            entityModel.add(webMvcLinkBuilder.withRel("all-users"));
            return entityModel;
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        Integer id = userService.saveUser(user).getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable(name = "id") Integer id){
        userService.deleteById(id);
    }

}
