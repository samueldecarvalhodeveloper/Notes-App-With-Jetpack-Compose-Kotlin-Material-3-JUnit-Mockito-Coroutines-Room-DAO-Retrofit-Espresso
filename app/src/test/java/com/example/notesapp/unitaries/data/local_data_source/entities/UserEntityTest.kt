package com.example.notesapp.unitaries.data.local_data_source.entities

import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.local_data_source.entities.UserEntity
import org.junit.Assert
import org.junit.Test

class UserEntityTest {
    @Test
    fun testIfEntityDescribesHowUserEntityShouldBeUsed() {
        val userEntity = UserEntity(USER_ID, USER_USERNAME)

        Assert.assertEquals(userEntity.id, USER_ID)
        Assert.assertEquals(userEntity.username, USER_USERNAME)
    }

    @Test
    fun testIfMethodGetNoteExternalModelReturnsCastedExternalModel() {
        val userEntity = UserEntity(USER_ID, USER_USERNAME)

        val user = userEntity.getUserExternalModel()

        Assert.assertEquals(user.id, USER_ID)
        Assert.assertEquals(user.username, USER_USERNAME)
    }
}