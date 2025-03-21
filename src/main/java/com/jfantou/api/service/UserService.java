package com.jfantou.api.service;

import com.jfantou.api.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 0;

    public List<User> findAll(){
        return users;
    }

    public User getUserById(int id){
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public User saveUser(User user){
        user.setId(usersCount++);
        users.add(user);
        return user;
    }

    public void deleteById(int id){
        users.removeIf(u -> u.getId() == id);
    }

}
