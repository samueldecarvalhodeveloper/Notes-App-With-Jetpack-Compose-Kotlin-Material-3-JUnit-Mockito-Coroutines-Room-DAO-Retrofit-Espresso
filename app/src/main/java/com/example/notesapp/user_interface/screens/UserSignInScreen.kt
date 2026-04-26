package com.example.notesapp.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.user_interface.theme.NEUTRALS_100
import com.example.notesapp.user_interface.theme.NEUTRALS_900
import com.example.notesapp.user_interface.theme.PRIMARY_500
import com.example.notesapp.user_interface.theme.SECONDARY_500
import com.example.notesapp.user_interface.theme.SECONDARY_900
import com.example.notesapp.user_interface.view_models.UserViewModel

@Composable
fun UserSignInScreen(userViewModel: UserViewModel, navController: NavController) {
    var userUsername by rememberSaveable { mutableStateOf("") }
    val isUserUsernameInvalid by userViewModel.isUserUsernameInvalidState.observeAsState()
    val isInternetErrorRisen by userViewModel.isInternetErrorRisenState.observeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PRIMARY_500
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Center
        ) {
            Column(
                modifier = Modifier
                    .shadow(6.dp)
                    .zIndex(1f)
                    .clip(RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp))
                    .background(NEUTRALS_100)
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = spacedBy(4.dp),
            ) {
                OutlinedTextField(
                    value = userUsername,
                    colors = colors(
                        focusedBorderColor = PRIMARY_500,
                        unfocusedBorderColor = PRIMARY_500,
                        focusedContainerColor = NEUTRALS_100,
                        unfocusedContainerColor = NEUTRALS_100,
                        focusedTextColor = NEUTRALS_900,
                        unfocusedTextColor = NEUTRALS_900,
                        focusedLabelColor = PRIMARY_500,
                        unfocusedLabelColor = PRIMARY_500,
                    ),
                    supportingText = {
                        if (isUserUsernameInvalid!!) {
                            Text(text = "Not valid username")
                        }

                        if (isInternetErrorRisen!!) {
                            Text(text = "No internet connection")
                        }
                    },
                    isError = isUserUsernameInvalid!! || isInternetErrorRisen!!,
                    onValueChange = { userUsername = it },
                    label = { Text(text = "Username") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = Text),
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    colors = ButtonColors(
                        containerColor = SECONDARY_500,
                        contentColor = SECONDARY_900,
                        disabledContainerColor = SECONDARY_500,
                        disabledContentColor = SECONDARY_900,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        userViewModel.createUser(userUsername) {
                            navController.navigate(route = NOTES_LISTING_SCREEN)
                        }
                    }
                ) {
                    Text(text = "Create user")
                }
            }
        }
    }
}