package com.example.notesapp.unitaries.data.remote_data_source.models

import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.remote_data_source.models.UserModel
import org.junit.Assert.assertEquals
import org.junit.Test

class UserModelTest {
    @Test
    fun testIfModelDescribesHowUserShouldHoldDataToTheService() {
        val userModel = UserModel(USER_USERNAME)

        assertEquals(USER_USERNAME, userModel.username)
    }
}