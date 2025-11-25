package com.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import com.example.notesapp.user_interface.components.CreateNoteFloatingActionButton
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CreateNoteFloatingActionButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfButtonIsDisplayedAndDispatchesActionOnClick() {
        var valueToBeToggled = false
        
        composeTestRule.setContent {
            CreateNoteFloatingActionButton {
                valueToBeToggled = true
            }
        }

        composeTestRule.onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT)
            .performClick()

        assertTrue(valueToBeToggled)
    }
}