package com.example.notesapp.unitaries.user_interface.screens

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_USER_BUTTON_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOT_AVAILABLE_INTERNET_ERROR_MESSAGE
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOT_VALID_USERNAME_ERROR_MESSAGE
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.USERNAME_TEXT_INPUT_LABEL
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.USER_SIGN_IN_SCREEN
import com.example.notesapp.mocks.UserCreationUserViewModelMock
import com.example.notesapp.user_interface.screens.UserSignInScreen
import com.example.notesapp.user_interface.view_models.UserViewModel
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`

class UserSignInScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfNotValidUsernameErrorMessageIsDisplayedWhenNotValidUserUsernameIsSubmitted() {
        `when`(userViewModel.isUserUsernameInvalidState)
            .thenReturn(MutableLiveData(true))
        `when`(userViewModel.isInternetErrorRisenState)
            .thenReturn(MutableLiveData(false))

        composeTestRule.setContent {
            UserSignInScreen(userViewModel = userViewModel, navController = navController)
        }

        composeTestRule.onNodeWithText(CREATE_USER_BUTTON_TEXT).performClick()

        composeTestRule.onNodeWithText(NOT_VALID_USERNAME_ERROR_MESSAGE).assertExists()
    }

    @Test
    fun testIfNotAvailableInternetErrorMessageIsDisplayedWhenInternetIsNotAvailableOnUserCreationSubmission() {
        `when`(userViewModel.isUserUsernameInvalidState)
            .thenReturn(MutableLiveData(false))
        `when`(userViewModel.isInternetErrorRisenState)
            .thenReturn(MutableLiveData(true))

        composeTestRule.setContent {
            UserSignInScreen(userViewModel = userViewModel, navController = navController)
        }

        composeTestRule.onNodeWithText(CREATE_USER_BUTTON_TEXT).performClick()

        composeTestRule.onNodeWithText(NOT_AVAILABLE_INTERNET_ERROR_MESSAGE).assertExists()
    }

    @Test
    fun testIfUserInterfaceNavigatesToUserListOfNotesIfUserIsCreated() {
        val context = ApplicationProvider.getApplicationContext() as Context

        navController = TestNavHostController(context)

        userViewModel = spy(UserCreationUserViewModelMock::class.java)

        `when`(userViewModel.isUserUsernameInvalidState)
            .thenReturn(MutableLiveData(false))
        `when`(userViewModel.isInternetErrorRisenState)
            .thenReturn(MutableLiveData(false))

        composeTestRule.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            NavHost(
                navController = navController as TestNavHostController,
                startDestination = USER_SIGN_IN_SCREEN
            ) {
                composable(USER_SIGN_IN_SCREEN) {
                    UserSignInScreen(userViewModel = userViewModel, navController = navController)
                }

                composable(NOTES_LISTING_SCREEN) {
                }
            }
        }

        composeTestRule.onNodeWithText(USERNAME_TEXT_INPUT_LABEL)
            .performTextInput(USER_USERNAME)

        composeTestRule.onNodeWithText(CREATE_USER_BUTTON_TEXT)
            .performClick()

        assertEquals(
            NOTES_LISTING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )
    }

    companion object {
        private lateinit var userViewModel: UserViewModel
        private lateinit var navController: NavController

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            userViewModel = mock(UserViewModel::class.java)
            navController = mock(NavController::class.java)
        }
    }
}
