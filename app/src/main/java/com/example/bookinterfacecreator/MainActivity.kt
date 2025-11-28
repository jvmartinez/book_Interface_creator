package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SocialFeedChallenge()
                }
            }
        }
    }
}

@Composable
fun SocialFeedChallenge() {
    LazyColumn {
        items(10) { index ->
            Postcard(
                user = "Usuario ${index + 1}",
                content = "Este es un post de ejemplo en mi red social #Compose",
                hour = "${index + 1}h",
                likes = index * 5
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Postcard(user: String, content: String, hour: String, likes: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF2196F3), CircleShape)
            ) {
                Text(
                    user.first().toString(),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(user, fontWeight = FontWeight.Bold)
                Text(hour, color = Color.Gray, fontSize = 12.sp)
            }

            Text("⋯", fontSize = 20.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Contenido
        Text(content)

        Spacer(modifier = Modifier.height(12.dp))

        // Imagen (opcional)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
        ) {
            Text(
                "Imagen del post",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Footer - Acciones
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionPost(icono = "❤️", texto = "$likes")
            ActionPost(icono = "💬", texto = "Comentar")
            ActionPost(icono = "↗️", texto = "Compartir")
        }
    }
}

@Composable
fun ActionPost(icono: String, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(icono)
        Spacer(modifier = Modifier.width(4.dp))
        Text(texto, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BookInterfaceCreatorTheme {
        SocialFeedChallenge()
    }
}