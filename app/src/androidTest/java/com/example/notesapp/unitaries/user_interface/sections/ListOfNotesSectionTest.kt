package com.example.notesapp.unitaries.user_interface.sections

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.user_interface.sections.ListOfNotesSection
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ListOfNotesSectionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfListOfNotesIsDisplayedAndItemsAreClickable() {
        var valueToBeToggled = false

        composeTestRule.setContent {
            ListOfNotesSection(listOfNotes = listOf(NOTE_OBJECT), onNoteItemClick = {
                valueToBeToggled = !valueToBeToggled
            })
        }

        composeTestRule.onNodeWithText(NOTE_TITLE).assertIsDisplayed().performClick()

        assertTrue(valueToBeToggled)
    }
}