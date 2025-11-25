package com.example.notesapp.system

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
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
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_USER_BUTTON_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.USERNAME_TEXT_INPUT_LABEL
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.USER_SIGN_IN_SCREEN
import com.example.notesapp.mocks.NoteCreationNotesListingViewModelMock
import com.example.notesapp.mocks.NoteDeletionNoteEditingViewModelMock
import com.example.notesapp.mocks.NoteUpdatingNoteEditingViewModelMock
import com.example.notesapp.mocks.UserCreationUserViewModelMock
import com.example.notesapp.user_interface.screens.NoteManipulatingScreen
import com.example.notesapp.user_interface.screens.NotesListingScreen
import com.example.notesapp.user_interface.screens.UserSignInScreen
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

class SystemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCreatingUserOnDatabaseAndServiceFromUserInterface() {
        val context = ApplicationProvider.getApplicationContext() as Context

        navController = TestNavHostController(context)

        userViewModel = spy(UserCreationUserViewModelMock::class.java)

        `when`(userViewModel.isUserUsernameInvalidState)
            .thenReturn(MutableLiveData(false))
        `when`(userViewModel.isInternetErrorRisenState)
            .thenReturn(MutableLiveData(false))
        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))

        composeTestRule.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            NavHost(
                navController = navController as TestNavHostController,
                startDestination = USER_SIGN_IN_SCREEN
            ) {
                composable(USER_SIGN_IN_SCREEN) {
                    UserSignInScreen(userViewModel = userViewModel, navController = navController)
                }

                composable(NOTES_LISTING_SCREEN) {
                }
            }
        }

        composeTestRule.onNodeWithText(USERNAME_TEXT_INPUT_LABEL)
            .performTextInput(USER_USERNAME)

        composeTestRule.onNodeWithText(CREATE_USER_BUTTON_TEXT).performClick()

        val createdUser = userViewModel.userState.value!!

        assertEquals(
            NOTES_LISTING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )

        assertEquals(USER_ID, createdUser.id)
        assertEquals(USER_USERNAME, createdUser.username)
    }

    @Test
    fun testFetchingNotesFromServerAndStoringOnDatabaseFromUserInterface() {
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

    @Test
    fun testCreatingNoteOnDatabaseAndServiceFromUserInterface() {
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

        assertEquals(
            NOTE_MANIPULATING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )

        assertEquals(NOTE_ID, noteEditingViewModel.noteState.value!!.id)
        assertEquals(NOTE_TITLE, noteEditingViewModel.noteState.value!!.title)
        assertEquals(NOTE_BODY, noteEditingViewModel.noteState.value!!.body)
        assertEquals(NOTE_CREATED_AT, noteEditingViewModel.noteState.value!!.createdAt)
        assertEquals(NOTE_UPDATED_AT, noteEditingViewModel.noteState.value!!.updatedAt)
        assertEquals(USER_ID, noteEditingViewModel.noteState.value!!.userId)
    }

    @Test
    fun testUpdatingNoteOnDatabaseAndServiceFromUserInterface() {
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

    @Test
    fun testDeletingNoteOnDatabaseAndServiceFromUserInterface() {
        val context = ApplicationProvider.getApplicationContext() as Context

        navController = TestNavHostController(context)

        noteEditingViewModel = spy(NoteDeletionNoteEditingViewModelMock::class.java)

        `when`(noteEditingViewModel.isNoteManipulationAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteBeingManipulatedState)
            .thenReturn(MutableLiveData(false))
        `when`(noteEditingViewModel.noteState)
            .thenReturn(MutableLiveData(NOTE_OBJECT))

        composeTestRule.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            NavHost(
                navController = navController as TestNavHostController,
                startDestination = NOTE_MANIPULATING_SCREEN
            ) {
                composable(NOTE_MANIPULATING_SCREEN) {
                    NoteManipulatingScreen(
                        noteId = NOTE_ID,
                        noteEditingViewModel = noteEditingViewModel,
                        navController = navController
                    )
                }

                composable(NOTES_LISTING_SCREEN) {
                }
            }
        }

        composeTestRule.onNodeWithContentDescription(DELETE_NOTE_CONTENT_DESCRIPTION)
            .performClick()

        assertEquals(
            NOTES_LISTING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )
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