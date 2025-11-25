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
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import com.example.notesapp.mocks.NoteDeletionNoteEditingViewModelMock
import com.example.notesapp.user_interface.screens.NoteManipulatingScreen
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`

class UserInterfaceDeletingNoteIntegrationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUserInterfaceDeletingNoteOnServerAndOnDatabase() {
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