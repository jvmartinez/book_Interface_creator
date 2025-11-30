package com.example.bookinterfacecreator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookinterfacecreator.core.TaskMasterNavigation
import com.example.bookinterfacecreator.presentation.TaskViewModel
import com.example.bookinterfacecreator.ui.theme.BookInterfaceCreatorTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookInterfaceCreatorTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) {
                    val vm: TaskViewModel = viewModel()
                    TaskMasterNavigation(taskViewModel = vm)
                }
            }
        }
    }
}
