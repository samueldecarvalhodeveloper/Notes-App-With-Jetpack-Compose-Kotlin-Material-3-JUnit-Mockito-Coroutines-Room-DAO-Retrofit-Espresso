package com.example.notesapp.unitaries.user_interface.infrastructure.factories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.user_interface.infrastructure.factories.UserViewModelFactory
import junit.framework.TestCase.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserViewModelFactoryTest {
    @Test
    fun testIfMethodGetInstanceReturnsAWorkingInstance() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val userViewModel = UserViewModelFactory.getInstance(context)

        assertFalse(userViewModel.userState.isInitialized)
    }
}