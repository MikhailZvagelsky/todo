package com.example.todo.controllers;

import com.example.todo.models.TodoList;
import com.example.todo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("todos")
public class TodosController {

    private final TodoService todoService;

    @GetMapping
    public TodoList getTodos() {
        return todoService.getTodos();
    }

    @PostMapping
    public void addTodo(@RequestParam("text") String newTodoText) {
        todoService.saveTodo(newTodoText);
    }

}
