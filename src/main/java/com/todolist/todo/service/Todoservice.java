package com.todolist.todo.service;

import com.todolist.todo.model.CompletedTodo;
import com.todolist.todo.model.Todo;

import java.util.List;
import java.util.Optional;

public interface Todoservice {
    Todo saveTodo(Todo todo);
    List<Todo> getAlltasks();
    Optional<Todo> findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);

    void moveTaskToCompleted(Integer id);

    List<CompletedTodo> viewcompleted();

}
