package com.example.notesapp.unitaries.user_interface.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.TEXT_INPUT_PLACEHOLDER_TEXT
import com.example.notesapp.user_interface.components.TextInput
import com.example.notesapp.user_interface.theme.NEUTRALS_300
import com.example.notesapp.user_interface.theme.NEUTRALS_900
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class TextInputTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfActionIsDispatchedOnTextChange() {
        var valueToBeToggled = false

        composeTestRule.setContent {
            TextInput(
                value = "",
                placeholder = TEXT_INPUT_PLACEHOLDER_TEXT,
                singleLine = false,
                textColor = NEUTRALS_900,
                placeholderColor = NEUTRALS_300,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier
                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
                    .fillMaxSize(),
                onValueChange = {
                    valueToBeToggled = !valueToBeToggled
                }
            )
        }

        composeTestRule.onNodeWithText(TEXT_INPUT_PLACEHOLDER_TEXT).performTextInput(NOTE_TITLE)

        assertTrue(valueToBeToggled)
    }
}