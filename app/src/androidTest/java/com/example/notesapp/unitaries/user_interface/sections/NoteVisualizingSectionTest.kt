package com.example.notesapp.unitaries.user_interface.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.user_interface.sections.NoteVisualizingSection
import org.junit.Rule
import org.junit.Test

class NoteVisualizingSectionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfNoteDataIsDisplayed() {
        composeTestRule.setContent {
            NoteVisualizingSection(note = NOTE_OBJECT, innerPadding = PaddingValues(0.dp))
        }
        
        composeTestRule.onNodeWithText(NOTE_TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithText(NOTE_BODY).assertIsDisplayed()

    }
}