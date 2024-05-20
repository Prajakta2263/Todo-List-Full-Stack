package com.todolist.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todolist.todo.model.Todo;
import org.springframework.stereotype.Repository;

@Repository

public interface Todorepository extends JpaRepository<Todo,Integer> {
}
