package com.example.todo.dao;

import com.example.todo.models.TimestampedTodo;

import java.util.List;

public interface TodoDao {
    List<TimestampedTodo> getTodos();

    void save(TimestampedTodo timestampedTodo);
}
