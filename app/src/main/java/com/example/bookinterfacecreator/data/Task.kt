package com.example.bookinterfacecreator.data

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val priority: Priority = Priority.MEDIUM,
    val dueDate: String? = null,
    val category: String = "Personal",
    val isCompleted: Boolean = false,
    val createdAt: String = getCurrentDateTime(),
    val updatedAt: String = getCurrentDateTime()
) {
    val isOverdue: Boolean
        get() = dueDate?.let {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(it)?.before(Date()) ?: false
        } ?: false
}

enum class Priority(val color: Color, val emoji: String) {
    LOW(Color(0xFF4CAF50), "🟢"),
    MEDIUM(Color(0xFF2196F3), "🔵"),
    HIGH(Color(0xFFFF9800), "🟠"),
    URGENT(Color(0xFFF44336), "🔴")
}

private fun getCurrentDateTime(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
}