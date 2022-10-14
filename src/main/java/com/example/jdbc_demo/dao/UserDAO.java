package com.example.jdbc_demo.dao;

import com.example.jdbc_demo.model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    User findById(Long id);

    Long create(User user);

    void update(User user);

    void delete(Long id);
}
