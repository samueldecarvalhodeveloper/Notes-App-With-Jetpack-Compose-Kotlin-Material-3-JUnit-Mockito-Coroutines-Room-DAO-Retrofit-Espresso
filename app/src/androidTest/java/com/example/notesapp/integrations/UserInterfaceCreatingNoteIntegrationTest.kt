package com.example.notesapp.integrations

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import com.example.notesapp.mocks.NoteCreationNotesListingViewModelMock
import com.example.notesapp.user_interface.screens.NotesListingScreen
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel
import com.example.notesapp.user_interface.view_models.NotesListingViewModel
import com.example.notesapp.user_interface.view_models.UserViewModel
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`

class UserInterfaceCreatingNoteIntegrationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testUserInterfaceCreatingNoteOnServerAndOnDatabase() {
        val context = ApplicationProvider.getApplicationContext() as Context

        navController = TestNavHostController(context)

        notesListingViewModel = spy(NoteCreationNotesListingViewModelMock::class.java)

        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))
        `when`(notesListingViewModel.isNoteCreationCurrentlyAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.isListOfNotesLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.listOfNotesState)
            .thenReturn(MutableLiveData(listOf(NOTE_OBJECT)))
        `when`(noteEditingViewModel.noteState)
            .thenReturn(MutableLiveData(NOTE_OBJECT))

        composeTestRule.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            NavHost(
                navController = navController as TestNavHostController,
                startDestination = NOTES_LISTING_SCREEN
            ) {
                composable(NOTES_LISTING_SCREEN) {
                    NotesListingScreen(
                        notesListingViewModel = notesListingViewModel,
                        userViewModel = userViewModel,
                        navController = navController
                    )
                }

                composable(NOTE_MANIPULATING_SCREEN) {
                }
            }
        }

        composeTestRule.onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT)
            .performClick()

        val createdNote = noteEditingViewModel.noteState.value!!

        assertEquals(
            NOTE_MANIPULATING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )

        assertEquals(NOTE_ID, createdNote.id)
        assertEquals(NOTE_TITLE, createdNote.title)
        assertEquals(NOTE_BODY, createdNote.body)
        assertEquals(NOTE_CREATED_AT, createdNote.createdAt)
        assertEquals(NOTE_UPDATED_AT, createdNote.updatedAt)
        assertEquals(USER_ID, createdNote.userId)
    }

    companion object {
        private lateinit var userViewModel: UserViewModel
        private lateinit var notesListingViewModel: NotesListingViewModel
        private lateinit var noteEditingViewModel: NoteEditingViewModel
        private lateinit var navController: NavController

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            userViewModel = mock(UserViewModel::class.java)
            notesListingViewModel = mock(NotesListingViewModel::class.java)
            noteEditingViewModel = mock(NoteEditingViewModel::class.java)
            navController = mock(NavController::class.java)
        }
    }
}