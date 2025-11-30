package com.example.bookinterfacecreator.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookinterfacecreator.R
import com.example.bookinterfacecreator.core.Destinations
import com.example.bookinterfacecreator.data.Task
import com.example.bookinterfacecreator.presentation.TaskViewModel
import com.example.bookinterfacecreator.presentation.data.TaskStats
import com.example.bookinterfacecreator.presentation.enums.TaskFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    var showFilters by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "TaskMaster Pro 🚀",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Text("🔍")
                    }
                    IconButton(onClick = {
                        navController.navigate(Destinations.STATISTICS)
                    }) {
                        Text("📊")
                    }
                    IconButton(onClick = {
                        navController.navigate(Destinations.SETTINGS)
                    }) {
                        Text("⚙️")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Destinations.NEW_TASK) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Image(painterResource(R.drawable.ic_new_task), contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (showFilters) {
                SearchAndFilters(
                    viewModel = viewModel,
                    modifier = Modifier.padding(16.dp)
                )
            }

            QuickSummary(
                stats = uiState.stats,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )

            // Task list
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cargando tareas...")
                }
            } else if (uiState.tasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎉", fontSize = 48.sp)
                        Text(
                            "No hay tareas",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            "Toca el botón + para crear tu primera tarea",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.tasks) { task ->
                        TaskItem(
                            task = task,
                            onToggleComplete = { viewModel.toggleTaskCompletion(task.id) },
                            onClick = {
                                navController.navigate(
                                    Destinations.createTaskDetailRoute(task.id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchAndFilters(
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Search bar
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            placeholder = { Text("Buscar tareas...") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Text("🔍") },
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filters
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(TaskFilter.entries.toTypedArray()) { filter ->
                FilterChip(
                    filter = filter,
                    isSelected = viewModel.currentFilter == filter,
                    onFilterSelected = { viewModel.onFilterChange(filter) }
                )
            }
        }
    }
}

@Composable
fun FilterChip(
    filter: TaskFilter,
    isSelected: Boolean,
    onFilterSelected: () -> Unit
) {
    Text(
        text = "${filter.emoji} ${filter.label}",
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
            .clickable(onClick = onFilterSelected)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
fun QuickSummary(
    stats: TaskStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            QuickStat("Total", stats.total.toString(), "📋")
            QuickStat("Pendientes", stats.pending.toString(), "⏳")
            QuickStat("Completadas", stats.completed.toString(), "✅")
            if (stats.overdue > 0) {
                QuickStat("Vencidas", stats.overdue.toString(), "⚠️")
            }
        }
    }
}

@Composable
fun QuickStat(
    label: String,
    value: String,
    emoji: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            "$emoji $value",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            label,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Custom circular checkbox
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (task.isCompleted) task.priority.color
                        else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = task.priority.color,
                        shape = CircleShape
                    )
                    .clickable(onClick = onToggleComplete),
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Text("✓", color = MaterialTheme.colorScheme.onPrimary, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Task content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough
                    else TextDecoration.None
                )

                if (task.description.isNotBlank()) {
                    Text(
                        task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Metadata
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        task.priority.emoji,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        task.category,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (task.dueDate != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "📅 ${task.dueDate}",
                            style = MaterialTheme.typography.labelLarge,
                            color = if (task.isOverdue) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Priority indicator
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(task.priority.color, CircleShape)
            )
        }
    }
}