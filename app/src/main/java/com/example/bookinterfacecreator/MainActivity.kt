package com.example.bookinterfacecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
                    RegistrationForm()
                }
            }
        }
    }
}

@Composable
fun RegistrationForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccept by remember { mutableStateOf(false) }

    val passwordsMatch = password == confirmPassword
    val formValid = name.isNotBlank() &&
            email.contains("@") &&
            password.length >= 6 &&
            passwordsMatch &&
            termsAccept

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Crear Cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // Campo nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = name.isNotBlank() && name.length < 2
        )
        if (name.isNotBlank() && name.length < 2) {
            Text("El nombre debe tener al menos 2 caracteres", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = email.isNotBlank() && !email.contains("@")
        )
        if (email.isNotBlank() && !email.contains("@")) {
            Text("Ingresa un email válido", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = password.isNotBlank() && password.length < 6
        )
        if (password.isNotBlank() && password.length < 6) {
            Text("La contraseña debe tener al menos 6 caracteres", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmPassword.isNotBlank() && !passwordsMatch
        )
        if (confirmPassword.isNotBlank() && !passwordsMatch) {
            Text("Las contraseñas no coinciden", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox términos
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsAccept,
                onCheckedChange = { termsAccept = it }
            )
            Text("Acepto los términos y condiciones", modifier = Modifier.clickable {
                termsAccept = !termsAccept
            })
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de registro
        Button(
            onClick = {
                // Lógica de registro
                println("Registrando usuario: $name, $email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = formValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (formValid) Color(0xFF2196F3) else Color.LightGray
            )
        ) {
            Text("Crear Cuenta", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookInterfaceCreatorTheme {
        RegistrationForm()
    }
}