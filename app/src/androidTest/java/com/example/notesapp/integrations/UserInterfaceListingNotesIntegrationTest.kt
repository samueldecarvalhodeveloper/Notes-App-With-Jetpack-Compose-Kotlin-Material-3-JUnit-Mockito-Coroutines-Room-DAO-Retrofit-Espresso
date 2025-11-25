package com.example.notesapp.integrations

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import com.example.notesapp.user_interface.screens.NotesListingScreen
import com.example.notesapp.user_interface.view_models.NotesListingViewModel
import com.example.notesapp.user_interface.view_models.UserViewModel
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserInterfaceListingNotesIntegrationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListingNotesFromDatabaseOnUserInterface() {
        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))
        `when`(notesListingViewModel.isNoteCreationCurrentlyAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.isListOfNotesLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.listOfNotesState)
            .thenReturn(MutableLiveData(listOf(NOTE_OBJECT)))

        composeTestRule.setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText(NOTE_TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithText(NOTE_BODY).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT)
            .assertIsDisplayed()
    }

    companion object {
        private lateinit var userViewModel: UserViewModel
        private lateinit var notesListingViewModel: NotesListingViewModel
        private lateinit var navController: NavController

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            userViewModel = mock(UserViewModel::class.java)
            notesListingViewModel = mock(NotesListingViewModel::class.java)
            navController = mock(NavController::class.java)
        }
    }
}