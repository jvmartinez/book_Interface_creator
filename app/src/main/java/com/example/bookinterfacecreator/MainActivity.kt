package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookinterfacecreator.ui.theme.BookInterfaceCreatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookInterfaceCreatorTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    ModifierGallery()
                }
            }
        }
    }
}

@Composable
fun ModifierGallery() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tarjeta con múltiples modifiers
        Text(
            "Tarjeta Premium",
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFD700),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFB8860B),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    println("Tarjeta premium seleccionada")
                },
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón con gradiente (usando background personalizado)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
                .clickable { println("Botón con gradiente clickeado") }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Botón Mágico",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar circular con borde
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray, CircleShape)
                .border(3.dp, Color(0xFF2196F3), CircleShape)
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                "UI",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BookInterfaceCreatorTheme {
        ModifierGallery()
    }
}