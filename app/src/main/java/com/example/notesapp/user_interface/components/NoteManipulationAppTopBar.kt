package com.example.notesapp.user_interface.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.BACK_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.EDIT_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.user_interface.theme.NEUTRALS_100
import com.example.notesapp.user_interface.theme.PRIMARY_500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteManipulationAppTopBar(
    isManipulateNoteButtonAble: Boolean,
    isConcludeNoteButtonAble: Boolean,
    isDeleteNoteButtonAble: Boolean,
    onNavigationIconButtonClick: () -> Unit,
    onConcludeNoteIconButtonClick: () -> Unit,
    onEditNoteIconButtonClick: () -> Unit,
    onDeleteNoteIconButtonClick: () -> Unit,
) {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PRIMARY_500,
            titleContentColor = NEUTRALS_100
        ),
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp),
        navigationIcon = {
            IconButton(onClick = { onNavigationIconButtonClick() }) {
                Icon(
                    imageVector = AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = BACK_BUTTON_CONTENT_DESCRIPTION,
                    tint = NEUTRALS_100
                )
            }
        },
        actions = {
            if (isConcludeNoteButtonAble) {
                IconButton(onClick = { onConcludeNoteIconButtonClick() }) {
                    Icon(
                        imageVector = Filled.Check,
                        contentDescription = CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION,
                        tint = NEUTRALS_100
                    )
                }
            }

            if (isManipulateNoteButtonAble) {
                IconButton(onClick = { onEditNoteIconButtonClick() }) {
                    Icon(
                        imageVector = Filled.Create,
                        contentDescription = EDIT_NOTE_CONTENT_DESCRIPTION,
                        tint = NEUTRALS_100
                    )
                }
            }

            if (isDeleteNoteButtonAble) {
                IconButton(onClick = { onDeleteNoteIconButtonClick() }) {
                    Icon(
                        imageVector = Filled.Delete,
                        contentDescription = DELETE_NOTE_CONTENT_DESCRIPTION,
                        tint = NEUTRALS_100
                    )
                }
            }
        }
    )
}