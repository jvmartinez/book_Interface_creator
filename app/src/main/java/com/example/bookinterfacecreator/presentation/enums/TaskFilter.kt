package com.example.bookinterfacecreator.presentation.enums

enum class TaskFilter(val label: String, val emoji: String) {
    ALL("Todas", "📋"),
    PENDING("Pendientes", "⏳"),
    COMPLETED("Completadas", "✅"),
    OVERDUE("Vencidas", "⚠️")
}