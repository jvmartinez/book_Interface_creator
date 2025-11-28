package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    AppMusicComplete()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BookInterfaceCreatorTheme {
        AppMusicComplete()
    }
}



// Estado que será "hoisted"
data class StatePlayer(
    val currentSong: String = "",
    val playing: Boolean = false,
    val volume: Float = 0.5f,
    val progress: Float = 0f
)

// Componente stateless
@Composable
fun PlayerMusic(
    state: StatePlayer,
    onPlayPause: () -> Unit,
    onVolumenChange: (Float) -> Unit,
    onProgresoChange: (Float) -> Unit,
    onSiguiente: () -> Unit,
    onAnterior: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        // Información de la canción
        Text(
            state.currentSong.ifEmpty { "Selecciona una canción" },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de progreso
        ProgressBar(
            progress = state.progress,
            onProgresoChange = onProgresoChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Controles de reproducción
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón anterior
            IconButton(onClick = onAnterior) {
                Text("⏮️", fontSize = 24.sp)
            }

            // Play/Pause
            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Gray, CircleShape)
            ) {
                Text(
                    if (state.playing) "⏸️" else "▶️",
                    fontSize = 24.sp
                )
            }

            // Botón siguiente
            IconButton(onClick = onSiguiente) {
                Text("⏭️", fontSize = 24.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Control de volumen
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🔈", color = Color.White)
            Slider(
                value = state.volume,
                onValueChange = onVolumenChange,
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Gray
                )
            )
            Text("🔊", color = Color.White)
        }
    }
}

@Composable
fun ProgressBar(progress: Float, onProgresoChange: (Float) -> Unit) {
    Column {
        Slider(
            value = progress,
            onValueChange = onProgresoChange,
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.Gray
            )
        )
        Text(
            "${progress.toInt()}%",
            color = Color.White,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

// Uso del componente con estado hoisted
@Composable
fun AppMusicComplete() {
    var statePlayer by remember {
        mutableStateOf(
            StatePlayer(
                currentSong = "Canción Ejemplo - Artista",
                playing = false,
                volume = 0.7f,
                progress = 30f
            )
        )
    }

    PlayerMusic(
        state = statePlayer,
        onPlayPause = {
            statePlayer = statePlayer.copy(
                playing = !statePlayer.playing
            )
        },
        onVolumenChange = { nuevoVolumen ->
            statePlayer = statePlayer.copy(volume = nuevoVolumen)
        },
        onProgresoChange = { nuevoProgreso ->
            statePlayer = statePlayer.copy(progress = nuevoProgreso)
        },
        onSiguiente = {
            // Lógica para siguiente canción
            statePlayer = statePlayer.copy(progress = 0f)
        },
        onAnterior = {
            // Lógica para canción anterior
            statePlayer = statePlayer.copy(progress = 0f)
        },
        modifier = Modifier.padding(16.dp)
    )
}