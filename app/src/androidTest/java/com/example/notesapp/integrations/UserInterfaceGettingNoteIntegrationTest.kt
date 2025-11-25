package com.example.notesapp.integrations

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.user_interface.screens.NoteManipulatingScreen
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserInterfaceGettingNoteIntegrationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testGettingNoteFromDatabaseOnUserInterface() {
        `when`(noteEditingViewModel.isNoteManipulationAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteBeingManipulatedState)
            .thenReturn(MutableLiveData(false))
        `when`(noteEditingViewModel.noteState)
            .thenReturn(MutableLiveData(NOTE_OBJECT))

        composeTestRule.setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText(NOTE_TITLE).assertIsDisplayed()

        composeTestRule.onNodeWithText(NOTE_BODY).assertIsDisplayed()
    }

    companion object {
        private lateinit var noteEditingViewModel: NoteEditingViewModel
        private lateinit var navController: NavController

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            noteEditingViewModel = mock(NoteEditingViewModel::class.java)
            navController = mock(NavController::class.java)
        }
    }
}