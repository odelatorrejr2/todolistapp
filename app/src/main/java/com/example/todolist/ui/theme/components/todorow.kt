package com.example.todolist.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todolist.model.TodoItem
import com.example.todolist.ui.theme.Purple40  // Your new green primary color

@Composable
fun TodoRow(
    item: TodoItem,
    onToggle: (TodoItem) -> Unit,
    onDelete: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isDone,
            onCheckedChange = { onToggle(item) },
            colors = CheckboxDefaults.colors(
                checkedColor = Purple40,      // Green checkbox
                checkmarkColor = Color.White,
                uncheckedColor = Purple40.copy(alpha = 0.4f)
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.text,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = { onDelete(item) }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete",
                tint = Purple40  // Green delete icon
            )
        }
    }
}
