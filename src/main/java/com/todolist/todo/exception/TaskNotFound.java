package com.todolist.todo.exception;

public class TaskNotFound extends Throwable {
    public TaskNotFound(Integer id){
        super("Could not find the task with id : "+id);
    }
}
