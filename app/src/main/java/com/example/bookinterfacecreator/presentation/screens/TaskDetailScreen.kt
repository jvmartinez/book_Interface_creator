package com.example.bookinterfacecreator.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookinterfacecreator.R
import com.example.bookinterfacecreator.data.Priority
import com.example.bookinterfacecreator.data.Task
import com.example.bookinterfacecreator.presentation.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navController: NavController,
    taskId: String,
    viewModel: TaskViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    // Prefer direct repository lookup to guarantee finding the task
    val task: Task? = viewModel.getTaskById(taskId) ?: uiState.tasks.find { it.id == taskId }

    var isEditing by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf("") }
    var editDescription by remember { mutableStateOf("") }
    var editPriority by remember { mutableStateOf(Priority.MEDIUM) }
    var editCategory by remember { mutableStateOf("") }
    var editDueDate by remember { mutableStateOf<String?>(null) }

    if (task != null && !isEditing && editTitle.isEmpty()) {
        editTitle = task.title
        editDescription = task.description
        editPriority = task.priority
        editCategory = task.category
        editDueDate = task.dueDate
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de tarea") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {

            if (task == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tarea no encontrada", style = MaterialTheme.typography.titleMedium)
                }
                return@Box
            }

            // Make the main content vertically scrollable so the edit form can expand
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // left: title & description
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                maxLines = 2,
                                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = if (task.description.isNotBlank()) task.description else "Sin descripción",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
                                maxLines = 3
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // chips row
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                // priority chip
                                Box(modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(task.priority.color.copy(alpha = 0.15f))
                                    .width(100.dp)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text("${task.priority.emoji} ${task.priority.name}", style = MaterialTheme.typography.labelLarge)
                                }

                                // category chip
                                Box(modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .width(100.dp)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(task.category, style = MaterialTheme.typography.labelLarge)
                                }

                                // due date chip
                                if (task.dueDate != null) {
                                    Box(modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .width(100.dp)
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text("📅 ${task.dueDate}", style = MaterialTheme.typography.labelLarge)
                                    }
                                }
                            }
                        }

                        // right: status & actions
                        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            // status indicator
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (task.isCompleted) Color(0xFF4CAF50) else Color(0xFFBDBDBD)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(if (task.isCompleted) "✓" else "•", color = Color.White)
                            }

                            // actions
                            Button(
                                modifier = Modifier.width(120.dp),
                                onClick = { viewModel.toggleTaskCompletion(task.id) }
                            ) {
                                Text(if (task.isCompleted) "Deshacer" else "Completar")
                            }

                            Button(
                                modifier = Modifier.width(120.dp),
                                onClick = { isEditing = true }
                            ) {
                                Text("Editar")
                            }
                        }
                    }
                }

                // Details / metadata card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Detalles", style = MaterialTheme.typography.titleMedium)

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("ID:", style = MaterialTheme.typography.labelLarge)
                            Text(task.id, style = MaterialTheme.typography.bodySmall)
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Creado:", style = MaterialTheme.typography.labelLarge)
                            Text(task.createdAt, style = MaterialTheme.typography.bodySmall)
                        }

                        if (task.dueDate != null) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("Vence:", style = MaterialTheme.typography.labelLarge)
                                Text(task.dueDate, style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Categoría:", style = MaterialTheme.typography.labelLarge)
                            Text(task.category, style = MaterialTheme.typography.bodySmall)
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Prioridad:", style = MaterialTheme.typography.labelLarge)
                            Text(task.priority.name, style = MaterialTheme.typography.bodySmall)
                        }

                        // delete action
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = {
                                viewModel.deleteTask(task.id)
                                navController.popBackStack()
                            }) {
                                Text("Eliminar tarea")
                            }
                        }
                    }
                }

                // Edit mode
                if (isEditing) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Editar tarea", style = MaterialTheme.typography.titleMedium)

                            OutlinedTextField(
                                value = editTitle,
                                onValueChange = { editTitle = it },
                                label = { Text("Título") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = editDescription,
                                onValueChange = { editDescription = it },
                                label = { Text("Descripción") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text("Prioridad")
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(Priority.entries) { p ->
                                    Box(modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { editPriority = p }
                                        .background(if (editPriority == p) p.color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Text("${p.emoji} ${p.name}")
                                    }
                                }
                            }

                            Text("Categoría")
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(viewModel.categories) { cat ->
                                    Box(modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { editCategory = cat }
                                        .background(if (editCategory == cat) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Text(cat)
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = editDueDate ?: "",
                                onValueChange = { editDueDate = if (it.isBlank()) null else it },
                                label = { Text("Fecha de vencimiento (yyyy-MM-dd)") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Button(onClick = {
                                    // Save
                                    val updated = task.copy(
                                        title = editTitle,
                                        description = editDescription,
                                        priority = editPriority,
                                        category = editCategory,
                                        dueDate = editDueDate
                                    )
                                    viewModel.updateTask(updated)
                                    isEditing = false
                                }) {
                                    Text("Guardar")
                                }

                                Button(onClick = {
                                    // Cancel
                                    isEditing = false
                                    editTitle = task.title
                                    editDescription = task.description
                                    editPriority = task.priority
                                    editCategory = task.category
                                    editDueDate = task.dueDate
                                }) {
                                    Text("Cancelar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
