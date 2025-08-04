package com.example.mytask.data

data class Task(
    val id: String? = null,           // Firestore document ID
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val priority: String = "",
    val category: String = "",
    var completed: Boolean = false,
    val userId: String = ""           // Logged-in user's UID for ownership
)
