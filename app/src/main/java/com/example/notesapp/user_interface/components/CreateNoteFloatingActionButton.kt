package com.example.notesapp.user_interface.components

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import com.example.notesapp.user_interface.theme.SECONDARY_500
import com.example.notesapp.user_interface.theme.SECONDARY_900

@Composable
fun CreateNoteFloatingActionButton(onNoteCreated: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = { Icon(Filled.Create, null) },
        text = { Text(text = "Create note") },
        contentColor = SECONDARY_900,
        containerColor = SECONDARY_500,
        modifier = Modifier.semantics {
            contentDescription = CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
        },
        onClick = {
            onNoteCreated()
        }
    )
}