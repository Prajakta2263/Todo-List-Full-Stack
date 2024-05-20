package com.todolist.todo.service;
import com.todolist.todo.model.CompletedTodo;
import com.todolist.todo.model.Todo;
import com.todolist.todo.repository.CompletedTodoRepository;
import com.todolist.todo.repository.Todorepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Todoserviceimple implements Todoservice {
    @Autowired
    private Todorepository todoRepository;
    @Autowired
    private CompletedTodoRepository completedtodorepository;
    @Override
    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getAlltasks() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> findById(Integer id) {
        return todoRepository.findById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return todoRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id){
        todoRepository.deleteById(id);
    }

    @Override
    public void moveTaskToCompleted(Integer id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            CompletedTodo completedTodo = new CompletedTodo();
            completedTodo.setTask(todo.getTask());
            completedtodorepository.save(completedTodo);
            todoRepository.deleteById(id);
        }
    }

    public List<CompletedTodo> viewcompleted() {
        return completedtodorepository.findAll();
    }
}
