package com.example.jdbc_demo.dao;

import com.example.jdbc_demo.model.Task;

import java.util.List;

public interface TaskDAO {

    List<Task> findAll();

    Task findById(Long id);

    List<Task> findByUserId(Long id);

    void create(Task task);

    void update(Task task);

    void delete(Long id);
}
