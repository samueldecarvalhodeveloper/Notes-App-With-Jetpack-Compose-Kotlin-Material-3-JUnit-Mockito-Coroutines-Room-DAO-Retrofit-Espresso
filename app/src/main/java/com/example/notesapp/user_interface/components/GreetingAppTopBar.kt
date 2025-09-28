package com.example.notesapp.user_interface.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.TOP_BAR_GREETING_TITLE_TEXT
import com.example.notesapp.user_interface.theme.NEUTRALS_100
import com.example.notesapp.user_interface.theme.PRIMARY_500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingAppTopBar(userUsername: String) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = PRIMARY_500,
            titleContentColor = NEUTRALS_100
        ),
        title = { Text(text = TOP_BAR_GREETING_TITLE_TEXT(userUsername)) }
    )
}