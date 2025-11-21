package com.example.todolist.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.todolist.model.TodoItem
import com.example.todolist.ui.theme.components.TodoRow
import com.example.todolist.util.IdGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppScreen(
    modifier: Modifier = Modifier
) {
    val activeItems = rememberSaveable { mutableStateListOf<TodoItem>() }
    val completedItems = rememberSaveable { mutableStateListOf<TodoItem>() }
    var textInput by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("To-Do List") })
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    label = { Text("Add a task") }
                )

                Spacer(Modifier.width(8.dp))

                Button(onClick = {
                    val trimmed = textInput.trim()
                    if (trimmed.isEmpty()) {
                        Toast.makeText(context, "Please enter a task", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val newItem = TodoItem(
                        id = IdGenerator.nextId(),
                        text = trimmed,
                        isDone = false
                    )

                    activeItems.add(newItem)
                    textInput = ""
                }) {
                    Text("Add")
                }
            }

            Spacer(Modifier.height(24.dp))

            if (activeItems.isNotEmpty()) {
                Text("Items", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                LazyColumn {
                    items(activeItems, key = { it.id }) { item ->
                        TodoRow(
                            item = item,
                            onToggle = { clickedItem ->
                                // move to completed
                                activeItems.remove(clickedItem)
                                completedItems.add(clickedItem.copy(isDone = true))
                            },
                            onDelete = { clickedItem ->
                                activeItems.remove(clickedItem)
                            }
                        )
                    }
                }
            } else {
                Text("No items yet", modifier = Modifier.padding(vertical = 8.dp))
            }

            Spacer(Modifier.height(24.dp))

            // Completed items section
            if (completedItems.isNotEmpty()) {
                Text("Completed Items", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                LazyColumn {
                    items(completedItems, key = { it.id }) { item ->
                        TodoRow(
                            item = item,
                            onToggle = { clickedItem ->
                                // move back to active
                                completedItems.remove(clickedItem)
                                activeItems.add(clickedItem.copy(isDone = false))
                            },
                            onDelete = { clickedItem ->
                                completedItems.remove(clickedItem)
                            }
                        )
                    }
                }
            } else {
                Text("No completed items", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
