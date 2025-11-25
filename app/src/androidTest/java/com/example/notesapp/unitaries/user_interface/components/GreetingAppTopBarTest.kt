package com.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.TOP_BAR_GREETING_TITLE_TEXT
import com.example.notesapp.user_interface.components.GreetingAppTopBar
import org.junit.Rule
import org.junit.Test

class GreetingAppTopBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfGreetingWithUserUsernameIsDisplayed() {
        composeTestRule.setContent {
            GreetingAppTopBar(userUsername = USER_USERNAME)
        }

        composeTestRule.onNodeWithText(TOP_BAR_GREETING_TITLE_TEXT(USER_USERNAME))
            .assertIsDisplayed()
    }
}