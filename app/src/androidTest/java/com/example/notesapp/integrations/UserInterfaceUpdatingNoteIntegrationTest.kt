package com.example.notesapp.integrations

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.navigation.NavController
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.mocks.NoteUpdatingNoteEditingViewModelMock
import com.example.notesapp.user_interface.screens.NoteManipulatingScreen
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class UserInterfaceUpdatingNoteIntegrationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUserInterfaceUpdatingNoteOnServerAndOnDatabase() {
        noteEditingViewModel = NoteUpdatingNoteEditingViewModelMock()

        composeTestRule.setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText(NOTE_TITLE).performTextClearance()
        composeTestRule.onNodeWithText(NOTE_BODY).performTextClearance()
        composeTestRule.onNodeWithContentDescription(CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION)
            .performClick()

        assertEquals(
            "",
            (noteEditingViewModel as NoteUpdatingNoteEditingViewModelMock)
                .noteTitleBeingManipulated
        )
        assertEquals(
            "",
            (noteEditingViewModel as NoteUpdatingNoteEditingViewModelMock)
                .noteBodyBeingManipulated
        )

        assertEquals("", noteEditingViewModel.noteState.value!!.title)
        assertEquals("", noteEditingViewModel.noteState.value!!.body)
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