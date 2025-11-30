package com.example.bookinterfacecreator.core

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookinterfacecreator.presentation.TaskViewModel
import com.example.bookinterfacecreator.presentation.screens.*

object Destinations {
    const val TASK_LIST = "task_list"
    const val TASK_DETAIL = "task_detail/{taskId}"
    const val NEW_TASK = "new_task"
    const val STATISTICS = "statistics"
    const val SETTINGS = "settings"

    fun createTaskDetailRoute(taskId: String) = "task_detail/$taskId"
}

@Composable
fun TaskMasterNavigation(taskViewModel: TaskViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.TASK_LIST
    ) {
        composable(Destinations.TASK_LIST) {
            TaskListScreen(
                navController = navController,
                viewModel = taskViewModel
            )
        }

        composable(Destinations.NEW_TASK) {
            NewTaskScreen(
                navController = navController,
                viewModel = taskViewModel
            )
        }

        composable(Destinations.STATISTICS) {
            StatisticsScreen(
                navController = navController,
                viewModel = taskViewModel
            )
        }

        composable(Destinations.SETTINGS) {
            SettingsScreen(
                navController = navController,
                viewModel = taskViewModel
            )
        }

        composable(
            route = Destinations.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            TaskDetailScreen(
                navController = navController,
                taskId = taskId,
                viewModel = taskViewModel
            )
        }
    }
}