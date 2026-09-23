package com.example.demo

import java.util.concurrent.ConcurrentHashMap

data class Task(val id: String, val title: String)

class TaskService {
    private val store = ConcurrentHashMap<String, Task>()

    fun addTask(task: Task) {
        store[task.id] = task
    }

    fun getTask(id: String): Task? = store[id]
}
