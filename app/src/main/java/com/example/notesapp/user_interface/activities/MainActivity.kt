package com.example.notesapp.user_interface.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_ARGUMENT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.USER_SIGN_IN_SCREEN
import com.example.notesapp.user_interface.infrastructure.factories.NoteEditingViewModelFactory
import com.example.notesapp.user_interface.infrastructure.factories.NotesListingViewModelFactory
import com.example.notesapp.user_interface.infrastructure.factories.UserViewModelFactory
import com.example.notesapp.user_interface.screens.NoteManipulatingScreen
import com.example.notesapp.user_interface.screens.NotesListingScreen
import com.example.notesapp.user_interface.screens.UserSignInScreen
import com.example.notesapp.user_interface.view_models.UserViewModel

class MainActivity : ComponentActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = UserViewModelFactory.getInstance(applicationContext)

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                userViewModel.verifyIfUserExists {
                    navController.navigate(route = NOTES_LISTING_SCREEN)
                }

                NavHost(navController = navController, startDestination = USER_SIGN_IN_SCREEN) {
                    composable(USER_SIGN_IN_SCREEN) {
                        UserSignInScreen(
                            userViewModel = userViewModel,
                            navController = navController
                        )
                    }

                    composable(NOTES_LISTING_SCREEN) {
                        val notesListingViewModel =
                            remember { NotesListingViewModelFactory.getInstance(applicationContext) }

                        NotesListingScreen(
                            notesListingViewModel = notesListingViewModel,
                            userViewModel = userViewModel,
                            navController = navController
                        )
                    }

                    composable(
                        route = NOTE_MANIPULATING_SCREEN,
                        arguments = listOf(navArgument(NOTE_ID_ARGUMENT) { type = IntType })
                    ) {
                        val noteId = it.arguments!!.getInt(NOTE_ID_ARGUMENT)

                        val noteEditingViewModel =
                            remember { NoteEditingViewModelFactory.getInstance(applicationContext) }

                        NoteManipulatingScreen(
                            noteId = noteId,
                            noteEditingViewModel = noteEditingViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
