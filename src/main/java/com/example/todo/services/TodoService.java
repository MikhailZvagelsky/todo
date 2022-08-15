package com.example.todo.services;

import com.example.todo.models.TodoList;

public interface TodoService {

    TodoList getTodos();

    void saveTodo(String text);
}
