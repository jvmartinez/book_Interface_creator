package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SimulatorLikes(innerPadding)
                }
            }
        }
    }
}

@Composable
fun SimulatorLikes(innerPadding: PaddingValues) {
    var likes by remember { mutableStateOf(0) }
    var dislikes by remember { mutableStateOf(0) }
    var comments by remember { mutableStateOf(mutableListOf("primer comentario", "segundo comentario")) }
    var newComment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        // Estadísticas
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatisticsInteraction("❤️", "Likes", likes)
            StatisticsInteraction("💔", "Dislikes", dislikes)
            StatisticsInteraction("💬", "Comentarios", comments.size)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de interacción
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ButtonAction("❤️ Dar Like") { likes++ }
            ButtonAction("💔 Dislike") { dislikes++ }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de comentarios
        Text("Comentarios:", fontWeight = FontWeight.Bold)

        // Lista de comentarios
        LazyColumn(modifier = Modifier.fillMaxWidth().height(240.dp)) {
            items(comments) { value ->
                Text(
                    "• $value",
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            }
        }

        // Agregar comentario
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = newComment,
                onValueChange = { newComment = it },
                placeholder = { Text("Escribe un comentario...") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newComment.isNotBlank()) {
                        comments.add(newComment)
                        newComment = ""
                    }
                },
                enabled = newComment.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            ) {
                Text("➕")
            }
        }
    }
}

@Composable
fun StatisticsInteraction(emoji: String, text: String, valor: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$emoji $valor", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun ButtonAction(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {
        Text(text, color = Color(0xFF1976D2))
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BookInterfaceCreatorTheme {
        SimulatorLikes(PaddingValues(0.dp))
    }
}