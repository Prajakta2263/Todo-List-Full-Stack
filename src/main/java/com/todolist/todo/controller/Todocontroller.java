package com.todolist.todo.controller;

import com.todolist.todo.exception.TaskNotFound;
import com.todolist.todo.model.CompletedTodo;
import com.todolist.todo.model.Todo;
import com.todolist.todo.service.Todoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "*")
public class Todocontroller {

    @Autowired
    private Todoservice todoservice;

    @PostMapping("/add")
    public String add(@RequestBody Todo todo) {
        todoservice.saveTodo(todo);
        return "New task added";
    }

    @GetMapping("/displaytasks")
    public List<Todo> getAlltasks(){
        return todoservice.getAlltasks();
    }

    @GetMapping("/task/{id}")
    public Todo getTaskById(@PathVariable Integer id) throws TaskNotFound {
        return todoservice.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));
    }

    @DeleteMapping("/task/{id}")
    public String deleteTask(@PathVariable Integer id) throws TaskNotFound {
        if (!todoservice.existsById(id)) {
            throw new TaskNotFound(id);
        }
        todoservice.deleteById(id);
        return "Task deleted";
    }

    @PostMapping("/task/{id}/complete")
    public String completeTask(@PathVariable Integer id) throws TaskNotFound {
        if (!todoservice.existsById(id)) {
            throw new TaskNotFound(id);
        }
        todoservice.moveTaskToCompleted(id);
        return "Task moved to completed";
    }

    @GetMapping("/viewcompleted")
    public List<CompletedTodo> viewcompleted(){
        return todoservice.viewcompleted();
    }
}
