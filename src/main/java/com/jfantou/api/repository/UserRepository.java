package com.jfantou.api.repository;

import com.jfantou.api.model.Post;
import com.jfantou.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>  {
}
