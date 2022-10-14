package com.example.jdbc_demo.dao;

import com.example.jdbc_demo.model.Direction;

import java.util.List;

public interface DirectionDAO {

    List<Direction> findAll();

    Direction findById(Long id);

    Long create(Direction direction);

    void update(Direction direction);

    void delete(Long id);

    void deleteByUserId(Long id);
}
