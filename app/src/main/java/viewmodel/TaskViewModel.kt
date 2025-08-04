package com.example.mytask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mytask.repository.TaskRepository
import com.example.mytask.data.Task

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> get() = repository.allTasks

    fun getTasksByCategory(category: String): LiveData<List<Task>> {
        return repository.getTasksByCategory(category)
    }

    fun insert(task: Task, onComplete: (Boolean) -> Unit) {
        repository.insert(task, onComplete)
    }

    fun update(taskId: String, updatedTask: Task, onComplete: (Boolean) -> Unit) {
        repository.update(taskId, updatedTask, onComplete)
    }

    fun delete(taskId: String, onComplete: (Boolean) -> Unit) {
        repository.delete(taskId, onComplete)
    }

    fun loadTasks() {
        repository.getAllTasks()
    }
}
