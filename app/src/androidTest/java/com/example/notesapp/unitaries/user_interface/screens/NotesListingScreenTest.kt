package com.example.notesapp.unitaries.user_interface.screens

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.LOADING_SECTION_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NO_NOTES_LABEL_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.TOP_BAR_GREETING_TITLE_TEXT
import com.example.notesapp.mocks.NoteCreationNotesListingViewModelMock
import com.example.notesapp.user_interface.screens.NotesListingScreen
import com.example.notesapp.user_interface.view_models.NotesListingViewModel
import com.example.notesapp.user_interface.view_models.UserViewModel
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`

class NotesListingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfLoadingSectionIsDisplayedWhenListOfNotesIsNotLoaded() {
        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))
        `when`(notesListingViewModel.isNoteCreationCurrentlyAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.isListOfNotesLoadedState)
            .thenReturn(MutableLiveData(false))
        `when`(notesListingViewModel.listOfNotesState)
            .thenReturn(MutableLiveData(listOf()))

        composeTestRule.setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithContentDescription(LOADING_SECTION_CONTENT_DESCRIPTION)
            .assertIsDisplayed()
    }

    @Test
    fun testIfUsernameIsDisplayedOnTopBarGreeting() {
        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))
        `when`(notesListingViewModel.isNoteCreationCurrentlyAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.isListOfNotesLoadedState)
            .thenReturn(MutableLiveData(false))
        `when`(notesListingViewModel.listOfNotesState)
            .thenReturn(MutableLiveData(listOf()))

        composeTestRule.setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText(TOP_BAR_GREETING_TITLE_TEXT(USER_USERNAME))
            .assertIsDisplayed()
    }

    @Test
    fun testIfNoNotesSectionIsDisplayedWhenListOfNotesIsEmpty() {
        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))
        `when`(notesListingViewModel.isNoteCreationCurrentlyAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.isListOfNotesLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.listOfNotesState)
            .thenReturn(MutableLiveData(listOf()))

        composeTestRule.setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText(NO_NOTES_LABEL_TEXT).assertIsDisplayed()
    }

    @Test
    fun testIfListOfNotesIsLoadedOnComponentAfterNotesFetchingFromServiceAndStoringOnDatabaseCompositionAndIsDisplayed() {
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
    fun testIfNavigatesToNoteManipulatingScreenWhenNoteItemFromListOfNotesIsClicked() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))
        `when`(notesListingViewModel.isNoteCreationCurrentlyAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.isListOfNotesLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(notesListingViewModel.listOfNotesState)
            .thenReturn(MutableLiveData(listOf(NOTE_OBJECT)))

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

        composeTestRule.onNodeWithText(NOTE_TITLE).performClick()

        assertEquals(
            NOTE_MANIPULATING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )
    }

    @Test
    fun testIfCreateNoteFloatingActionButtonIsNotDisplayedWhenNoteCreationCurrentlyAbleIsFalse() {
        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))
        `when`(notesListingViewModel.isNoteCreationCurrentlyAbleState)
            .thenReturn(MutableLiveData(false))
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

        composeTestRule.onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT)
            .assertIsNotDisplayed()
    }

    @Test
    fun testIfNavigatesToNoteManipulatingScreenWhenNoteIsCreated() {
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
