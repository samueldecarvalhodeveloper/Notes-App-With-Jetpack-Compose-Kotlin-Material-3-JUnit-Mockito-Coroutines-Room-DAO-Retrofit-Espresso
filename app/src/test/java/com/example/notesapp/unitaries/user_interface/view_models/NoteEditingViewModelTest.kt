package com.example.notesapp.unitaries.user_interface.view_models

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_STATE_FIELD_NAME
import com.example.notesapp.data.repositories.NoteRepository
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import java.lang.reflect.Field

@RunWith(RobolectricTestRunner::class)
class NoteEditingViewModelTest {
    private lateinit var noteEditingViewModel: NoteEditingViewModel
    private lateinit var noteRepository: NoteRepository

    @Before
    fun beforeEach() {
        noteRepository = mock()

        noteEditingViewModel = NoteEditingViewModel(noteRepository)
    }

    @Test
    fun testIfMethodLoadNoteLoadsNoteFromDatabaseAndSetsNoteStateAndCurrentNoteBeingEditedAndTurnsIsNoteLoadedStateToTrue() =
        runTest {
            `when`(noteRepository.getNote(NOTE_ID))
                .thenReturn(NOTE_OBJECT)

            noteEditingViewModel.loadNote(NOTE_ID)

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertEquals(NOTE_ID, noteEditingViewModel.noteState.value!!.id)
            assertEquals(NOTE_TITLE, noteEditingViewModel.noteState.value!!.title)
            assertEquals(NOTE_BODY, noteEditingViewModel.noteState.value!!.body)
            assertEquals(NOTE_CREATED_AT, noteEditingViewModel.noteState.value!!.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteEditingViewModel.noteState.value!!.updatedAt)
            assertEquals(USER_ID, noteEditingViewModel.noteState.value!!.userId)

            assertTrue(noteEditingViewModel.isNoteLoadedState.value!!)
        }

    @Test
    fun testIfMethodManipulateNoteTurnsIsNoteBeingManipulatedStateToTrueIfIsNoteManipulationAbleStateIsTrue() {
        noteEditingViewModel.manipulateNote()

        assertTrue(noteEditingViewModel.isNoteBeingManipulatedState.value!!)
    }

    @Test
    fun testIfMethodConcludeNoteUpdatesNoteOnServiceAndOnDatabaseAndTurnsIsNoteBeingManipulatedStateToFalseAndUpdatesNoteState() =
        runTest {
            `when`(
                noteRepository.getNote(NOTE_ID)
            )
                .thenReturn(NOTE_OBJECT)
            `when`(
                noteRepository.getUpdatedNote(
                    NOTE_ID,
                    NOTE_TITLE,
                    NOTE_BODY,
                    USER_ID
                )
            )
                .thenReturn(NOTE_OBJECT)

            noteEditingViewModel.loadNote(NOTE_ID)

            noteEditingViewModel.concludeNote()

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertEquals(NOTE_ID, noteEditingViewModel.noteState.value!!.id)
            assertEquals(NOTE_TITLE, noteEditingViewModel.noteState.value!!.title)
            assertEquals(NOTE_BODY, noteEditingViewModel.noteState.value!!.body)
            assertEquals(
                NOTE_CREATED_AT,
                noteEditingViewModel.noteState.value!!.createdAt
            )
            assertEquals(
                NOTE_UPDATED_AT,
                noteEditingViewModel.noteState.value!!.updatedAt
            )
            assertEquals(
                USER_ID,
                noteEditingViewModel.noteState.value!!.userId
            )

            assertFalse(noteEditingViewModel.isNoteBeingManipulatedState.value!!)
        }

    @Test
    fun testIfMethodConcludeNoteOnNoteUpdatingFailTurnsIsNoteManipulationAbleStateToFalseIsNoteBeingManipulatedStateToFalse() =
        runTest {
            noteStateField.set(noteEditingViewModel, MutableLiveData(NOTE_OBJECT))

            `when`(noteRepository.getUpdatedNote(NOTE_ID, "", "", USER_ID))
                .thenAnswer {
                    throw Exception()
                }

            noteEditingViewModel.concludeNote()

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertFalse(noteEditingViewModel.isNoteManipulationAbleState.value!!)
            assertFalse(noteEditingViewModel.isNoteBeingManipulatedState.value!!)
        }

    @Test
    fun testIfMethodDeleteNoteDeletesNoteOnServiceAndOnDatabaseAndDispatchesItsAction() = runTest {
        var isNoteDeleted = false

        noteStateField.set(noteEditingViewModel, MutableLiveData(NOTE_OBJECT))

        noteRepository.deleteNote(0, 0)

        noteEditingViewModel.deleteNote {
            isNoteDeleted = true
        }

        assertTrue(isNoteDeleted)
    }

    @Test
    fun testIfMethodDeleteNoteTurnsIsNoteBeingManipulatedStateToFalseAndIsNoteManipulationAbleStateToFalse() =
        runTest {
            `when`(noteRepository.deleteNote(NOTE_ID, USER_ID))
                .thenAnswer {
                    throw Exception()
                }

            noteEditingViewModel.deleteNote {}

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertFalse(noteEditingViewModel.isNoteBeingManipulatedState.value!!)
            assertFalse(noteEditingViewModel.isNoteManipulationAbleState.value!!)
        }

    companion object {
        private lateinit var noteStateField: Field

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            noteStateField = NoteEditingViewModel::class.java
                .getDeclaredField(NOTE_STATE_FIELD_NAME)

            noteStateField.isAccessible = true
        }
    }
}