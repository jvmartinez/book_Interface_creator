package com.example.bookinterfacecreator.data

import androidx.compose.runtime.mutableStateListOf

class TaskRepository {
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    init {
        // Datos de ejemplo
        _tasks.addAll(
            listOf(
                Task(
                    title = "Completar proyecto Compose",
                    description = "Terminar la app de tareas TaskMaster Pro",
                    priority = Priority.HIGH,
                    dueDate = "2024-01-15",
                    category = "Trabajo"
                ),
                Task(
                    title = "Comprar víveres",
                    description = "Leche, huevos, pan y frutas",
                    priority = Priority.MEDIUM,
                    category = "Personal"
                ),
                Task(
                    title = "Reunión con el equipo",
                    description = "Revisión del sprint actual",
                    priority = Priority.URGENT,
                    dueDate = "2024-01-10",
                    category = "Trabajo"
                )
            )
        )
    }

    fun addTask(task: Task) {
        _tasks.add(0, task)
    }

    fun updateTask(updatedTask: Task) {
        val index = _tasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            _tasks[index] = updatedTask
        }
    }

    fun deleteTask(taskId: String) {
        _tasks.removeAll { it.id == taskId }
    }

    fun getTaskById(taskId: String): Task? {
        return _tasks.find { it.id == taskId }
    }

    fun searchTasks(query: String): List<Task> {
        return if (query.isBlank()) {
            tasks
        } else {
            tasks.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
        }
    }

    fun getTasksByCategory(category: String): List<Task> {
        return tasks.filter { it.category == category }
    }

    fun getCompletedTasks(): List<Task> {
        return tasks.filter { it.isCompleted }
    }

    fun getPendingTasks(): List<Task> {
        return tasks.filter { !it.isCompleted }
    }

    fun getOverdueTasks(): List<Task> {
        return tasks.filter { it.isOverdue && !it.isCompleted }
    }
}