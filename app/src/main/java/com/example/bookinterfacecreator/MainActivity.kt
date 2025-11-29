package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
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
                    AppNotesChallenge(innerPadding)
                }
            }
        }
    }
}

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val createDate: String,
    val favorite: Boolean = false
)

@Composable
fun AppNotesChallenge(innerPadding: PaddingValues) {
    var notes by remember { mutableStateOf(emptyList<Note>()) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var showOnlyFavorites by remember { mutableStateOf(false) }

    val notesFilters = if (showOnlyFavorites) {
        notes.filter { it.favorite }
    } else {
        notes
    }

    Column(modifier = Modifier
        .padding(innerPadding)
        .padding(16.dp)) {
        // Header
        Text("📝 Mis Notas", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // Filtro
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = showOnlyFavorites,
                onCheckedChange = { showOnlyFavorites = it }
            )
            Text("Mostrar solo favoritas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario nueva nota
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nueva Nota", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Contenido") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            val newNote = Note(
                                id = notes.size + 1,
                                title = title,
                                content = content,
                                createDate = "Hoy"
                            )
                            notes = notes + newNote
                            title = ""
                            content = ""
                        }
                    },
                    enabled = title.isNotBlank() && content.isNotBlank(),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Guardar Nota")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de notas
        LazyColumn {
            items(notesFilters) { nota ->
                NoteCard(
                    note = nota,
                    onToggleFavorite = {
                        notes = notes.map { n ->
                            if (n.id == nota.id) n.copy(favorite = !n.favorite) else n
                        }
                    },
                    onDelete = {
                        notes = notes.filter { it.id != nota.id }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NoteCard(
    note: Note,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(note.title, fontWeight = FontWeight.Bold)

                Row {
                    IconButton(onClick = onToggleFavorite) {
                        Text(if (note.favorite) "⭐" else "☆")
                    }
                    IconButton(onClick = onDelete) {
                        Text("🗑️")
                    }
                }
            }

            Text(note.content, modifier = Modifier.padding(vertical = 8.dp))

            Text(note.createDate, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BookInterfaceCreatorTheme {
        AppNotesChallenge(PaddingValues(0.dp))
    }
}