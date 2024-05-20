package com.todolist.todo.repository;

import com.todolist.todo.model.CompletedTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompletedTodoRepository extends JpaRepository<CompletedTodo, Integer> {
}
