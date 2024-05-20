import React, { useEffect, useState } from 'react';

const Home = () => {
    const [task, setTask] = useState('');
    const [todo, setTodo] = useState([]);
    const [completed, setCompleted] = useState([]);

    const handleClick = async (e) => {
        e.preventDefault();
        const todolist = { task, completed: false };

        // Clear the input field after adding the task
        setTask('');

        try {
            const response = await fetch("http://localhost:8080/todo/add", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(todolist)
            });

            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }

            console.log("New task added");
            // Fetch updated task list
            fetchTasks();
        } catch (error) {
            console.error("Failed to add new task:", error);
        }
    }

    const fetchTasks = async () => {
        try {
            const todoResponse = await fetch("http://localhost:8080/todo/displaytasks");
            const completedResponse = await fetch("http://localhost:8080/todo/viewcompleted");

            if (!todoResponse.ok || !completedResponse.ok) {
                throw new Error('Network response was not ok');
            }

            const todoResult = await todoResponse.json();
            const completedResult = await completedResponse.json();

            setTodo(todoResult);
            setCompleted(completedResult);
        } catch (error) {
            console.error("Failed to fetch tasks:", error);
        }
    }

    const handleCheckboxChange = async (id, completed) => {
        try {
            // Concurrently delete the task and move it to completed
            const [deleteResponse, completeResponse] = await Promise.all([
                fetch(`http://localhost:8080/todo/task/${id}`, {
                    method: "DELETE",
                }),
                fetch(`http://localhost:8080/todo/task/${id}/complete`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ completed })
                })
            ]);

            if (!deleteResponse.ok) {
                throw new Error('Failed to delete task ' + deleteResponse.statusText);
            }

            if (!completeResponse.ok) {
                throw new Error('Failed to move task to completed ' + completeResponse.statusText);
            }

            console.log("Task deleted and moved to completed");

            // Fetch updated task lists
            fetchTasks();
        } catch (error) {
            console.error("Failed to delete or move task:", error);
        }
    }

    useEffect(() => {
        fetchTasks();
    }, []);

    return (
        <div className='main'>
            <div>Todo List</div>
            <section>
                <div className='todo'>
                    <h1>TASKS TO DO</h1>
                    {todo.map(todoItem => (
                        <div key={todoItem.id}>
                            <input
                                type='checkbox'
                                checked={todoItem.completed}
                                onChange={() => handleCheckboxChange(todoItem.id, !todoItem.completed)}
                            />
                            {todoItem.task}
                        </div>
                    ))}
                </div>
                <div className='done'>
                    <h1>DONE</h1>
                    {completed.map(completedItem => (
                        <div key={completedItem.id}>
                            {completedItem.task}
                        </div>
                    ))}
                </div>
            </section>
            <section>
                <div className='addtask'>
                    <input
                        type='text'
                        value={task}
                        onChange={(e) => setTask(e.target.value)}
                    />
                    <button type='submit' onClick={handleClick}>+</button>
                    <h3>ADD TASK</h3>
                </div>
            </section>
        </div>
    );
}

export default Home;
