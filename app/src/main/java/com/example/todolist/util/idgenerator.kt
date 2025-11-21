package com.example.todolist.util

import java.util.concurrent.atomic.AtomicInteger

object IdGenerator {
    private val counter = AtomicInteger(0)
    fun nextId(): Int = counter.getAndIncrement()
}
