package com.example.notesapp.unitaries.user_interface.view_models

import android.os.Looper
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_WITH_EMPTY_DATA
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.repositories.NoteRepository
import com.example.notesapp.user_interface.view_models.NotesListingViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class NotesListingViewModelTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var notesListingViewModel: NotesListingViewModel

    @Before
    fun beforeEach() {
        noteRepository = mock()

        notesListingViewModel = NotesListingViewModel(noteRepository)
    }

    @Test
    fun testIfMethodLoadNotesRetrievesUserNotesFromServiceAndStoresThemOnDatabaseAndAddsThemToListOfNotesState() =
        runTest {
            `when`(noteRepository.getNotes())
                .thenReturn(listOf(NOTE_OBJECT))

            notesListingViewModel.loadNotes(USER_ID)

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertFalse(notesListingViewModel.listOfNotesState.value!!.isEmpty())
            assertTrue(notesListingViewModel.isListOfNotesLoadedState.value!!)
        }

    @Test
    fun testIfMethodCreateNoteCreatesNoteOnServiceAndDispatchesItsAction() = runTest {
        var isNoteCreated = false

        `when`(noteRepository.getCreatedNote("", "", USER_ID))
            .thenReturn(NOTE_WITH_EMPTY_DATA)

        notesListingViewModel.createNote(USER_ID) {
            isNoteCreated = true
        }

        assertTrue(isNoteCreated)
    }

    @Test
    fun testIfMethodCreateNoteTurnsIsNoteCreationCurrentlyAbleStateToFalseIfNoteCreationFails() =
        runTest {
            `when`(noteRepository.getCreatedNote("", "", USER_ID))
                .thenAnswer {
                    throw Exception()
                }

            notesListingViewModel.createNote(USER_ID) {}

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertFalse(notesListingViewModel.isNoteCreationCurrentlyAbleState.value!!)
        }
}