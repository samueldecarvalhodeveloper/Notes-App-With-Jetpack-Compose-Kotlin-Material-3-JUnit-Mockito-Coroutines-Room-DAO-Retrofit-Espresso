package com.example.notesapp.unitaries.user_interface.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.unit.dp
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.user_interface.sections.NoteEditingSection
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class NoteEditingSectionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfNoteDataIsDisplayedDispatchesTitleAndBodyActionsOnTextChange() {
        var firstValueToBeToggled = false
        var secondValueToBeToggled = false

        composeTestRule.setContent {
            NoteEditingSection(
                note = NOTE_OBJECT,
                innerPadding = PaddingValues(0.dp),
                onNoteTitleChange = {
                    firstValueToBeToggled = !firstValueToBeToggled
                },
                onNoteBodyChange = {
                    secondValueToBeToggled = !secondValueToBeToggled
                }
            )
        }

        composeTestRule.onNodeWithText(NOTE_TITLE).performTextClearance()
        composeTestRule.onNodeWithText(NOTE_BODY).performTextClearance()

        assertTrue(firstValueToBeToggled)
        assertTrue(secondValueToBeToggled)
    }
}