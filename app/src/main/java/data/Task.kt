package com.example.mytask.data

data class Task(
    val id: String? = null,           
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val priority: String = "",
    val category: String = "",
    var completed: Boolean = false,
    val userId: String = ""           )
