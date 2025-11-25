package com.example.notesapp.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_ARGUMENT_KEY
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import com.example.notesapp.user_interface.components.CreateNoteFloatingActionButton
import com.example.notesapp.user_interface.components.GreetingAppTopBar
import com.example.notesapp.user_interface.sections.ListOfNotesSection
import com.example.notesapp.user_interface.sections.LoadingSection
import com.example.notesapp.user_interface.sections.NoNotesSection
import com.example.notesapp.user_interface.theme.NEUTRALS_100
import com.example.notesapp.user_interface.view_models.NotesListingViewModel
import com.example.notesapp.user_interface.view_models.UserViewModel

@Composable
fun NotesListingScreen(
    notesListingViewModel: NotesListingViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {
    val user by userViewModel.userState.observeAsState()
    val isNoteCreationCurrentlyAble by notesListingViewModel.isNoteCreationCurrentlyAbleState.observeAsState()
    val isListOfNotesLoaded by notesListingViewModel.isListOfNotesLoadedState.observeAsState()
    val listOfNotes by notesListingViewModel.listOfNotesState.observeAsState()

    LaunchedEffect(Unit) {
        notesListingViewModel.loadNotes(user!!.id)
    }

    Scaffold(
        modifier = Modifier
            .background(NEUTRALS_100)
            .fillMaxSize(),
        topBar = { GreetingAppTopBar(user!!.username) },
        floatingActionButton = {
            if (isNoteCreationCurrentlyAble!! && isListOfNotesLoaded!!) {
                CreateNoteFloatingActionButton {
                    notesListingViewModel.createNote(userId = user!!.id) {
                        navController.navigate(
                            route = NOTE_MANIPULATING_SCREEN.replace(
                                NOTE_ID_ARGUMENT_KEY, it.toString()
                            )
                        )
                    }
                }
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isListOfNotesLoaded!!) {
                if (listOfNotes!!.isEmpty()) {
                    NoNotesSection()
                } else {
                    ListOfNotesSection(listOfNotes = listOfNotes!!) {
                        navController.navigate(
                            route = NOTE_MANIPULATING_SCREEN.replace(
                                NOTE_ID_ARGUMENT_KEY, it.toString()
                            )
                        )
                    }
                }
            } else {
                LoadingSection()
            }
        }
    }
}