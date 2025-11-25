package com.example.notesapp.unitaries.data.external_models

import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.external_models.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {
    @Test
    fun testIfEntityDescribesHowUserShouldBeUsedByExternalDomains() {
        val user = User(USER_ID, USER_USERNAME)

        assertEquals(USER_ID.toLong(), user.id.toLong())
        assertEquals(USER_USERNAME, user.username)
    }

    @Test
    fun testIfMethodGetUserEntityReturnsCastedDatabaseEntity() {
        val user = User(USER_ID, USER_USERNAME)

        val userEntity = user.getUserEntity()

        assertEquals(USER_ID.toLong(), userEntity.id.toLong())
        assertEquals(USER_USERNAME, userEntity.username)
    }
}