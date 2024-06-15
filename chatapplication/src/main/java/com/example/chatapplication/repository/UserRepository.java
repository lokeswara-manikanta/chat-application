package com.example.chatapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chatapplication.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
