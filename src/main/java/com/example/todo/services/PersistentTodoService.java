package com.example.todo.services;

import com.example.todo.dao.TodoDao;
import com.example.todo.models.TimestampedTodo;
import com.example.todo.models.Todo;
import com.example.todo.models.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class PersistentTodoService implements TodoService {

    private final TodoDao todoDao;

    @Override
    public TodoList getTodos() {
        List<TimestampedTodo> todoList = todoDao.getTodos();
        sort(todoList);
        List<Todo> todos = convert(todoList);
        return new TodoList(todos);
    }

    @Override
    public void saveTodo(String text) {
        todoDao.save(new TimestampedTodo(System.currentTimeMillis(), text));
    }

    private List<Todo> convert(List<TimestampedTodo> todoList) {
        return todoList
                .stream()
                .map(TimestampedTodo::text)
                .map(Todo::new)
                .collect(Collectors.toList());
    }

    private void sort(List<TimestampedTodo> todoList) {
        todoList.sort(Comparator.comparingLong(TimestampedTodo::millis));
    }
}
