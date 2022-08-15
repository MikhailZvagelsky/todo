package com.example.todo.models;

import java.util.Queue;

public record TodoList(Queue<Todo> todos) {
}
