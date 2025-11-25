package com.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.user_interface.components.NoteItem
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class NoteItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNoteDataIsDisplayedAndDispatchesActionOnClick() {
        var valueToBeToggled = false

        composeTestRule.setContent {
            NoteItem(note = NOTE_OBJECT) {
                valueToBeToggled = true
            }
        }

        composeTestRule.onNodeWithText(NOTE_TITLE).performClick()

        assertTrue(valueToBeToggled)
    }
}