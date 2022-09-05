package com.example.todo.controllers;

import com.example.todo.models.Todo;
import com.example.todo.services.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/todos")
//@CrossOrigin("localhost:3000")
public class TodosController {

    private final TodoService todoService;

    @GetMapping
    public List<String> getTodosText() {
        return todoService
                .getTodos()
                .todos()
                .stream()
                .map(Todo::text)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addTodo(@RequestBody Todo todo) {
        log.info("New todo: " + todo);
        todoService.saveTodo(todo.text());
    }

}
