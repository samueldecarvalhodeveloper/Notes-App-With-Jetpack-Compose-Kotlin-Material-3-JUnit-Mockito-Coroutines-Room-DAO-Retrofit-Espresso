package com.example.notesapp.integrations

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
import com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_USER_BUTTON_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
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

class UserInterfaceCreatingUserIntegrationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUserInterfaceCreatingUserOnServerAndOnDatabase() {
        val context = ApplicationProvider.getApplicationContext() as Context

        navController = TestNavHostController(context)

        userViewModel = spy(UserCreationUserViewModelMock::class.java)

        `when`(userViewModel.isUserUsernameInvalidState)
            .thenReturn(MutableLiveData(false))
        `when`(userViewModel.isInternetErrorRisenState)
            .thenReturn(MutableLiveData(false))
        `when`(userViewModel.userState)
            .thenReturn(MutableLiveData(USER_EXTERNAL_MODEL_OBJECT))

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

        composeTestRule.onNodeWithText(CREATE_USER_BUTTON_TEXT).performClick()

        val createdUser = userViewModel.userState.value!!

        assertEquals(
            NOTES_LISTING_SCREEN,
            navController.currentBackStackEntry!!.destination.route
        )

        assertEquals(USER_ID, createdUser.id)
        assertEquals(USER_USERNAME, createdUser.username)
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