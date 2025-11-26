package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                    RetoModifiers()
                }
            }
        }
    }
}

/*
 Crea un componente que muestre un precio con las siguientes características:
- Fondo con esquinas redondeadas
- Texto en negrita
- Borde de color
- Efecto al hacer clic
- Padding adecuado alrededor del texto*/

@Composable
fun RetoModifiers() {
    Box(
        modifier = Modifier
            .background(Color(0xFF4CAF50), RoundedCornerShape(12.dp))
            .border(2.dp, Color(0xFF388E3C), RoundedCornerShape(12.dp))
            .clickable { println("Precio clickeado") }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            "€ 29.99",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BookInterfaceCreatorTheme {
        RetoModifiers()
    }
}