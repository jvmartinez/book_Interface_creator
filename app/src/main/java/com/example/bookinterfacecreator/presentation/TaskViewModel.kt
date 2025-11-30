package com.example.bookinterfacecreator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bookinterfacecreator.data.Task
import com.example.bookinterfacecreator.data.TaskRepository
import com.example.bookinterfacecreator.presentation.data.TaskStats
import com.example.bookinterfacecreator.presentation.data.TaskUiState
import com.example.bookinterfacecreator.presentation.enums.TaskFilter

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    // Estado de la UI
    var uiState by mutableStateOf(TaskUiState())
        private set

    // Filtros y búsqueda
    var currentFilter by mutableStateOf(TaskFilter.ALL)
        private set

    var searchQuery by mutableStateOf("")
        private set

    // Categorías disponibles
    val categories = listOf("Personal", "Trabajo", "Estudio", "Salud", "Otros")

    init {
        loadTasks()
    }

    private fun loadTasks() {
        uiState = uiState.copy(
            tasks = repository.tasks,
            isLoading = false
        )
    }

    // Expose a safe lookup by id to screens
    fun getTaskById(taskId: String): Task? {
        return repository.getTaskById(taskId)
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        applyFilters()
    }

    fun onFilterChange(filter: TaskFilter) {
        currentFilter = filter
        applyFilters()
    }

    private fun applyFilters() {
        val filteredTasks = when (currentFilter) {
            TaskFilter.ALL -> repository.tasks
            TaskFilter.PENDING -> repository.getPendingTasks()
            TaskFilter.COMPLETED -> repository.getCompletedTasks()
            TaskFilter.OVERDUE -> repository.getOverdueTasks()
        }

        val searchedTasks = repository.searchTasks(searchQuery)
            .filter { filteredTasks.contains(it) }

        uiState = uiState.copy(
            tasks = searchedTasks,
            stats = calculateStats()
        )
    }

    fun addTask(task: Task) {
        repository.addTask(task)
        applyFilters()
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
        applyFilters()
    }

    fun deleteTask(taskId: String) {
        repository.deleteTask(taskId)
        applyFilters()
    }

    fun toggleTaskCompletion(taskId: String) {
        val task = repository.getTaskById(taskId)
        task?.let {
            repository.updateTask(it.copy(isCompleted = !it.isCompleted))
            applyFilters()
        }
    }

    private fun calculateStats(): TaskStats {
        val total = repository.tasks.size
        val completed = repository.getCompletedTasks().size
        val pending = repository.getPendingTasks().size
        val overdue = repository.getOverdueTasks().size

        return TaskStats(
            total = total,
            completed = completed,
            pending = pending,
            overdue = overdue
        )
    }
}