package com.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.BACK_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.EDIT_NOTE_CONTENT_DESCRIPTION
import com.example.notesapp.user_interface.components.NoteManipulationAppTopBar
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class NoteManipulationAppTopBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfButtonsDispatchesActionsOnClick() {
        var firstValueToBeToggled = false
        var secondValueToBeToggled = false
        var thirdValueToBeToggled = false
        var fourthValueToBeToggled = false

        composeTestRule.setContent {
            NoteManipulationAppTopBar(
                isManipulateNoteButtonAble = true,
                isConcludeNoteButtonAble = true,
                isDeleteNoteButtonAble = true,
                onNavigationIconButtonClick = {
                    firstValueToBeToggled = true
                },
                onEditNoteIconButtonClick = {
                    secondValueToBeToggled = true
                },
                onDeleteNoteIconButtonClick = {
                    thirdValueToBeToggled = true
                },
                onConcludeNoteIconButtonClick = {
                    fourthValueToBeToggled = true
                }
            )
        }

        composeTestRule.onNodeWithContentDescription(BACK_BUTTON_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithContentDescription(EDIT_NOTE_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithContentDescription(DELETE_NOTE_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithContentDescription(CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()

        assertTrue(firstValueToBeToggled)
        assertTrue(secondValueToBeToggled)
        assertTrue(thirdValueToBeToggled)
        assertTrue(fourthValueToBeToggled)
    }
}