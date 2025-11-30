package com.example.bookinterfacecreator.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bookinterfacecreator.R
import com.example.bookinterfacecreator.presentation.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    // Local (temporary) settings state - replace with persistent storage if desired
    var showCompleted by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.BY_DATE) }

    var showClearConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("App Settings", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Control how the app behaves and how tasks are displayed. Changes here are local to this device.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                // Display section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Display", style = MaterialTheme.typography.titleMedium)

                        SettingRow(title = "Show completed tasks", subtitle = "Toggle visibility of finished tasks") {
                            Switch(checked = showCompleted, onCheckedChange = { showCompleted = it })
                        }

                        SettingRow(title = "Enable notifications", subtitle = "Get reminders for due tasks") {
                            Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
                        }
                    }
                }

                // Sort section with chips
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Sort", style = MaterialTheme.typography.titleMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ChoiceChip(text = "By date", selected = sortOrder == SortOrder.BY_DATE) { sortOrder = SortOrder.BY_DATE }
                            ChoiceChip(text = "By priority", selected = sortOrder == SortOrder.BY_PRIORITY) { sortOrder = SortOrder.BY_PRIORITY }
                            ChoiceChip(text = "By title", selected = sortOrder == SortOrder.BY_TITLE) { sortOrder = SortOrder.BY_TITLE }
                        }
                        Text(
                            when (sortOrder) {
                                SortOrder.BY_DATE -> "Newest first"
                                SortOrder.BY_PRIORITY -> "High priority first"
                                SortOrder.BY_TITLE -> "Alphabetical"
                            },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Danger zone styled
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Danger Zone", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Clear all tasks will permanently remove every task from the local repository.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { showClearConfirm = true },
                                colors = ButtonDefaults.filledTonalButtonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Clear all tasks", color = MaterialTheme.colorScheme.onError)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(onClick = { /* future: export data */ }) {
                                Text("Export tasks")
                            }
                        }
                    }
                }

                // App info card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("App info", style = MaterialTheme.typography.titleMedium)
                        Text("Total tasks: ${uiState.tasks.size}")
                    }
                }
            }

            if (showClearConfirm) {
                AlertDialog(
                    onDismissRequest = { showClearConfirm = false },
                    title = { Text("Confirm clear all") },
                    text = { Text("Are you sure you want to delete all tasks? This action cannot be undone.") },
                    confirmButton = {
                        Button(onClick = {
                            // Delete all tasks safely by copying ids
                            val ids = uiState.tasks.map { it.id }
                            ids.forEach { viewModel.deleteTask(it) }
                            showClearConfirm = false
                        }) { Text("Delete all") }
                    },
                    dismissButton = {
                        Button(onClick = { showClearConfirm = false }) { Text("Cancel") }
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingRow(title: String, subtitle: String, action: @Composable () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        action()
    }
}

@Composable
private fun ChoiceChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = bg)) {
        Text(text, color = contentColor)
    }
}

private enum class SortOrder {
    BY_DATE, BY_PRIORITY, BY_TITLE
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingsScreen(navController = navController)
}
