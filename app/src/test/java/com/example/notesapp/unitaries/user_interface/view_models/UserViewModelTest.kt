package com.example.notesapp.unitaries.user_interface.view_models

import android.os.Looper
import com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.repositories.UserRepository
import com.example.notesapp.user_interface.view_models.UserViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class UserViewModelTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel

    @Test
    fun testIfVerifyIfUserExistsSetsUserStateAndDispatchesItsAction() =
        runTest {
            var isUserCreated = false
            userRepository = mock()

            `when`(userRepository.getUser())
                .thenReturn(USER_EXTERNAL_MODEL_OBJECT)

            userViewModel = UserViewModel(userRepository)

            userViewModel.verifyIfUserExists {
                isUserCreated = true
            }

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertEquals(USER_ID, userViewModel.userState.value!!.id)
            assertEquals(USER_USERNAME, userViewModel.userState.value!!.username)

            assertTrue(isUserCreated)
        }

    @Test
    fun testIfMethodCreateUserTurnsIsUserUsernameInvalidTrueIfUsernameIsEmpty() =
        runTest {
            userRepository = mock()

            userViewModel = UserViewModel(userRepository)

            userViewModel.createUser("") {}

            assertTrue(userViewModel.isUserUsernameInvalidState.value!!)
        }

    @Test
    fun testIfMethodCreateUserTurnsIsInternetErrorRisenTrueIfServiceAccessIsNotAvailable() =
        runTest {
            userRepository = mock()

            userViewModel = UserViewModel(userRepository)

            `when`(userRepository.getCreatedUser(USER_USERNAME))
                .thenAnswer {
                    Exception()
                }

            userViewModel.createUser(USER_USERNAME) {}

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertTrue(userViewModel.isInternetErrorRisenState.value!!)
        }

    @Test
    fun testIfMethodCreateUserCreatesUserAndAddsItToUserStateAndDispatchesItsAction() =
        runTest {
            var isUserCreated = false
            userRepository = mock()

            userViewModel = UserViewModel(userRepository)

            `when`(userRepository.getCreatedUser(USER_USERNAME))
                .thenReturn(USER_EXTERNAL_MODEL_OBJECT)

            userViewModel.createUser(USER_USERNAME) {
                isUserCreated = true
            }

            Shadows.shadowOf(Looper.getMainLooper()).idle()

            assertEquals(USER_ID, userViewModel.userState.value!!.id)
            assertEquals(USER_USERNAME, userViewModel.userState.value!!.username)

            assertFalse(userViewModel.isUserUsernameInvalidState.value!!)

            assertTrue(isUserCreated)
        }
}