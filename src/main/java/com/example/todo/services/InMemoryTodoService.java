package com.example.todo.services;

import com.example.todo.models.Todo;
import com.example.todo.models.TodoList;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class InMemoryTodoService implements TodoService {

    private final Queue<Todo> todoList = new ConcurrentLinkedQueue<>();

    public InMemoryTodoService() {
        todoList.add(new Todo("TODO 1"));
        todoList.add(new Todo("TODO 2"));
        todoList.add(new Todo("TODO 3"));
    }

    @Override
    public TodoList getTodos() {
        return new TodoList(todoList);
    }

    @Override
    public void saveTodo(String text) {
        todoList.add(new Todo(text));
    }
}
