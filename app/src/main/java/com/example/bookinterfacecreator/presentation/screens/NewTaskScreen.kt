package com.example.bookinterfacecreator.presentation.screens

import android.app.Activity
import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookinterfacecreator.R
import com.example.bookinterfacecreator.data.Priority
import com.example.bookinterfacecreator.data.Task
import com.example.bookinterfacecreator.presentation.TaskViewModel
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var category by remember { mutableStateOf("Personal") }
    var dueDate by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Tarea") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()) {
                                val newTask = Task(
                                    title = title,
                                    description = description,
                                    priority = priority,
                                    dueDate = dueDate,
                                    category = category
                                )
                                viewModel.addTask(newTask)
                                navController.popBackStack()
                            }
                        },
                        enabled = title.isNotBlank()
                    ) {
                        Text("💾")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title field
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = title.isBlank()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Priority selector
            Text("Prioridad", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(Priority.entries.toTypedArray()) { p ->
                    PriorityChip(
                        priority = p,
                        isSelected = priority == p,
                        onPrioritySelected = { priority = p }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Category selector
            Text("Categoría", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(viewModel.categories) { cat ->
                    CategoryChip(
                        category = cat,
                        isSelected = category == cat,
                        onCategorySelected = { category = cat }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Date selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Fecha de vencimiento", style = MaterialTheme.typography.titleMedium)
                    Text(
                        dueDate ?: "No establecida",
                        color = if (dueDate == null)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }

                Button(
                    onClick = {
                        // show DatePickerDialog immediately with current or parsed dueDate
                        val cal = Calendar.getInstance()
                        dueDate?.let { d ->
                            try {
                                val parts = d.split("-")
                                if (parts.size == 3) {
                                    val y = parts[0].toInt()
                                    val m = parts[1].toInt() - 1
                                    val day = parts[2].toInt()
                                    cal.set(y, m, day)
                                }
                            } catch (_: Exception) { /* ignore parse errors */ }
                        }

                        // Prefer Activity context if available (fixes some cases where LocalContext isn't an Activity)
                        val activity = context as? Activity
                        val dialogContext = activity ?: context

                        val dialog = DatePickerDialog(
                            dialogContext,
                            { _, year, month, dayOfMonth ->
                                val month1 = month + 1
                                dueDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month1, dayOfMonth)
                            },
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        )

                        dialog.setOnCancelListener { /* no-op */ }
                        dialog.show()
                    }
                ) {
                    Text("📅")
                }
            }

            // Additional info
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "💡 Consejo",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Las tareas con fechas de vencimiento te ayudarán a organizarte mejor. ¡No olvides establecer prioridades!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityChip(
    priority: Priority,
    isSelected: Boolean,
    onPrioritySelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onPrioritySelected),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) priority.color
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(priority.emoji)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                when (priority) {
                    Priority.LOW -> "Baja"
                    Priority.MEDIUM -> "Media"
                    Priority.HIGH -> "Alta"
                    Priority.URGENT -> "Urgente"
                },
                color = if (isSelected) Color.White
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onCategorySelected: () -> Unit
) {
    Text(
        text = category,
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
            .clickable(onClick = onCategorySelected)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant
    )
}