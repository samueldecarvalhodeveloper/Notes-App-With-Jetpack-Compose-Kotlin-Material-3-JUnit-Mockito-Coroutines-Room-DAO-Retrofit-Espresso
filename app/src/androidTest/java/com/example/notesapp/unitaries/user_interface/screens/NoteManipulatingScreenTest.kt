package com.example.notesapp.unitaries.user_interface.screens

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.BACK_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.EDIT_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.LOADING_SECTION_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import com.example.notesapp.mocks.NoteDeletionNoteEditingViewModelMock
import com.example.notesapp.mocks.NoteUpdatingNoteEditingViewModelMock
import com.example.notesapp.user_interface.screens.NoteManipulatingScreen
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`

class NoteManipulatingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfNavigatesWhenTopBarNavigationButton() {
        val context = ApplicationProvider.getApplicationContext() as Context

        navController = TestNavHostController(context)

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

        composeTestRule.onNodeWithContentDescription(BACK_BUTTON_CONTENT_DESCRIPTION)
            .performClick()

        assertEquals(
            NOTES_LISTING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )
    }

    @Test
    fun testIfTopBarConcludeButtonTurnsFalseIsNoteBeingManipulatedState() {
        `when`(noteEditingViewModel.isNoteManipulationAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteBeingManipulatedState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.noteState)
            .thenReturn(MutableLiveData(NOTE_OBJECT))

        composeTestRule.setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                navController = navController
            )
        }

        `when`(noteEditingViewModel.isNoteBeingManipulatedState)
            .thenReturn(MutableLiveData(false))

        composeTestRule.onNodeWithContentDescription(CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION)
            .performClick()

        assertFalse(noteEditingViewModel.isNoteBeingManipulatedState.value!!)
    }

    @Test
    fun testIfTopBarEditButtonTurnsTrueIsNoteBeingManipulatedState() {
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

        `when`(noteEditingViewModel.isNoteBeingManipulatedState)
            .thenReturn(MutableLiveData(true))

        composeTestRule.onNodeWithContentDescription(EDIT_NOTE_CONTENT_DESCRIPTION)
            .performClick()

        assertTrue(noteEditingViewModel.isNoteBeingManipulatedState.value!!)
    }

    @Test
    fun testIfTopBarDeleteButtonDeletesNoteOnDatabaseAndNavigateToNotesListingScreen() {
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

    @Test
    fun testIfNotesIsLoadedFromDatabaseAndIsDisplayed() {
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
    fun testIfNotesEditingSectionIsDisplayedWhenNoteBeingManipulatedIsTrue() {
        `when`(noteEditingViewModel.isNoteManipulationAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteLoadedState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteBeingManipulatedState)
            .thenReturn(MutableLiveData(true))
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
    fun testIfLoadingSectionIsDisplayedIfNotesIsNotLoaded() {
        `when`(noteEditingViewModel.isNoteManipulationAbleState)
            .thenReturn(MutableLiveData(true))
        `when`(noteEditingViewModel.isNoteLoadedState)
            .thenReturn(MutableLiveData(false))
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

        composeTestRule.onNodeWithContentDescription(LOADING_SECTION_CONTENT_DESCRIPTION)
            .assertIsDisplayed()
    }

    @Test
    fun testIfWhenNoteDataChangesUpdatesViewModelTitleAndBodyNoteValues() {
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
