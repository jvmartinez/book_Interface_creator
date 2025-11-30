package com.example.bookinterfacecreator.presentation.data

import com.example.bookinterfacecreator.data.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
    val stats: TaskStats = TaskStats()
)