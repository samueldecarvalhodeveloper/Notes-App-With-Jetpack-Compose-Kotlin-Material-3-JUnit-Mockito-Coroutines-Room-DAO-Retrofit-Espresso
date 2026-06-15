package com.example.notesapp.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.user_interface.components.NoteManipulationAppTopBar
import com.example.notesapp.user_interface.sections.LoadingSection
import com.example.notesapp.user_interface.sections.NoteEditingSection
import com.example.notesapp.user_interface.sections.NoteVisualizingSection
import com.example.notesapp.user_interface.theme.NEUTRALS_100
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel

@Composable
fun NoteManipulatingScreen(
    noteId: Int,
    noteEditingViewModel: NoteEditingViewModel,
    navController: NavController
) {
    val isNoteManipulationAble by noteEditingViewModel.isNoteManipulationAbleState.observeAsState()
    val isNoteLoaded by noteEditingViewModel.isNoteLoadedState.observeAsState()
    val isNoteBeingManipulated by noteEditingViewModel.isNoteBeingManipulatedState.observeAsState()
    val note by noteEditingViewModel.noteState.observeAsState()

    LaunchedEffect(Unit) {
        noteEditingViewModel.loadNote(noteId)
    }

    Scaffold(
        modifier = Modifier
            .background(NEUTRALS_100)
            .fillMaxSize(),
        topBar = {
            NoteManipulationAppTopBar(
                isManipulateNoteButtonAble = isNoteManipulationAble!! && isNoteLoaded!! && isNoteBeingManipulated!!.not(),
                isConcludeNoteButtonAble = isNoteManipulationAble!! && isNoteLoaded!! && isNoteBeingManipulated!!,
                isDeleteNoteButtonAble = isNoteManipulationAble!! && isNoteLoaded!!,
                onNavigationIconButtonClick = {
                    navController.navigate(route = NOTES_LISTING_SCREEN)
                },
                onConcludeNoteIconButtonClick = { noteEditingViewModel.concludeNote() },
                onEditNoteIconButtonClick = { noteEditingViewModel.manipulateNote() },
                onDeleteNoteIconButtonClick = {
                    noteEditingViewModel.deleteNote {
                        navController.navigate(route = NOTES_LISTING_SCREEN)
                    }
                },
            )
        }
    ) { innerPadding ->
        if (isNoteLoaded!!) {
            if (isNoteBeingManipulated!!) {
                NoteEditingSection(
                    note = note!!,
                    innerPadding = innerPadding,
                    onNoteTitleChange = {
                        noteEditingViewModel.setNoteTitle(it)
                    },
                    onNoteBodyChange = {
                        noteEditingViewModel.setNoteBody(it)
                    }
                )
            } else {
                NoteVisualizingSection(note!!, innerPadding)
            }
        } else {
            LoadingSection()
        }
    }
}
