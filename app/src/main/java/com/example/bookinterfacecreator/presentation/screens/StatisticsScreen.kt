package com.example.bookinterfacecreator.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookinterfacecreator.R
import com.example.bookinterfacecreator.presentation.TaskViewModel
import com.example.bookinterfacecreator.data.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estadísticas 📊") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
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
            // Resumen general
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Resumen General",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StatisticItem("Total de tareas", uiState.stats.total.toString(), "📋")
                    StatisticItem("Completadas", uiState.stats.completed.toString(), "✅")
                    StatisticItem("Pendientes", uiState.stats.pending.toString(), "⏳")
                    if (uiState.stats.overdue > 0) {
                        StatisticItem("Vencidas", uiState.stats.overdue.toString(), "⚠️")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Porcentaje de completado
                    val percentage = if (uiState.stats.total > 0) {
                        (uiState.stats.completed * 100) / uiState.stats.total
                    } else 0

                    Text("Progreso general: $percentage%")
                    LinearProgressIndicator(
                        progress = { percentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Distribución por prioridad
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Por Prioridad", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))

                    Priority.entries.forEach { priority ->
                        val count = uiState.tasks.count { it.priority == priority }
                        DistributionItem(
                            label = when (priority) {
                                Priority.LOW -> "Baja"
                                Priority.MEDIUM -> "Media"
                                Priority.HIGH -> "Alta"
                                Priority.URGENT -> "Urgente"
                            },
                            count = count,
                            emoji = priority.emoji,
                            color = priority.color
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticItem(label: String, value: String, emoji: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("$emoji $label")
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DistributionItem(label: String, count: Int, emoji: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$emoji $label", modifier = Modifier.weight(1f))
        Text("$count", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, CircleShape)
        )
    }
}