package com.example.todolist.model

data class TodoItem(
    val id: Int,
    val text: String,
    val isDone: Boolean = false
)