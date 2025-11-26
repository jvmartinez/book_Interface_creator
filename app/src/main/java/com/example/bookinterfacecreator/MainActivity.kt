package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookinterfacecreator.ui.theme.BookInterfaceCreatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookInterfaceCreatorTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    AppTaskComplete()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTaskComplete() {
    var textTask by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Tareas 📝") },
                actions = {
                    IconButton(onClick = { /* Acción */ }) {
                        Text("🔍")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Agregar tarea */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+", fontSize = 20.sp)
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Todas", fontWeight = FontWeight.Bold)
                    Text("Pendientes")
                    Text("Completadas")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de texto para nueva tarea
            OutlinedTextField(
                value = textTask,
                onValueChange = { textTask = it },
                label = { Text("Nueva tarea") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de tareas
            LazyColumn {
                items(5) { index ->
                    TaskCard(
                        task = "Tarea importante #${index + 1}",
                        isComplete = index % 2 == 0
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: String, isComplete: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isComplete) Color(0xFFE8F5E8) else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isComplete) Color.Green else Color.Transparent,
                    shape = CircleShape
                )
                .border(2.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            task,
            color = if (isComplete) Color.Gray else Color.Black,
            textDecoration = if (isComplete) TextDecoration.LineThrough else null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BookInterfaceCreatorTheme {
        AppTaskComplete()
    }
}