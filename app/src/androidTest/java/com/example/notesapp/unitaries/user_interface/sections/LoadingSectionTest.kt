package com.example.notesapp.unitaries.user_interface.sections

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.LOADING_SECTION_CONTENT_DESCRIPTION
import com.example.notesapp.user_interface.sections.LoadingSection
import org.junit.Rule
import org.junit.Test

class LoadingSectionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfLoadingProgressIndicatorIsDisplayed() {
        composeTestRule.setContent {
            LoadingSection()
        }

        composeTestRule.onNodeWithContentDescription(LOADING_SECTION_CONTENT_DESCRIPTION)
            .assertIsDisplayed()
    }
}