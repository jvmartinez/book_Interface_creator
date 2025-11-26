package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                    NotificationBadge()
                }
            }
        }
    }
}

@Composable
fun NotificationBadge() {
    Box {
        // Icono principal
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Blue, CircleShape)
        ) {
            Text(
                "✉️",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp
            )
        }

        // Badge de notificación
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.Red, CircleShape)
                .align(Alignment.TopEnd)
        ) {
            Text(
                "3",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookInterfaceCreatorTheme {
        NotificationBadge()
    }
}