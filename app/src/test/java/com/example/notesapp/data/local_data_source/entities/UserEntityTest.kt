package com.example.notesapp.data.local_data_source.entities

import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import org.junit.Assert
import org.junit.Test

class UserEntityTest {
    @Test
    fun testIfMethodGetNoteExternalModelReturnsCastedModel() {
        val userEntity = UserEntity(USER_ID, USER_USERNAME)

        val user = userEntity.getUserModel()

        Assert.assertEquals(user.id, USER_ID)
        Assert.assertEquals(user.username, USER_USERNAME)
    }
}