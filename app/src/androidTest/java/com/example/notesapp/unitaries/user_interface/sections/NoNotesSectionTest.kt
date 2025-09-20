package com.example.notesapp.unitaries.user_interface.sections

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NO_NOTES_LABEL_TEXT
import com.example.notesapp.user_interface.sections.NoNotesSection
import org.junit.Rule
import org.junit.Test

class NoNotesSectionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfNoNotesLabelIsDisplayed() {
        composeTestRule.setContent {
            NoNotesSection()
        }

        composeTestRule.onNodeWithText(NO_NOTES_LABEL_TEXT).assertIsDisplayed()
    }
}