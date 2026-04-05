package com.example.notesapp.data.remote_data_source.data_transfer_objects

import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDataTransferObjectTest {
    @Test
    fun testIfModelDescribesHowUserShouldSendDataToTheService() {
        val userDataTransferObject = UserDataTransferObject(USER_USERNAME)

        assertEquals(USER_USERNAME, userDataTransferObject.username)
    }
}