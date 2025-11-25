package com.example.notesapp.mocks

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import com.example.notesapp.data.external_models.User
import com.example.notesapp.data.local_data_source.databases.UserDatabase
import com.example.notesapp.data.remote_data_source.services.UserService
import com.example.notesapp.data.repositories.UserRepository
import com.example.notesapp.user_interface.view_models.UserViewModel

private val context = ApplicationProvider.getApplicationContext() as Context

private val userDatabase = inMemoryDatabaseBuilder(context, UserDatabase::class.java)
    .allowMainThreadQueries()
    .build()

private val userDataAccessObject = userDatabase.getDataAccessObject()
private val userGateway = UserService.getInstance(SERVICE_URL)

private val userRepository = UserRepository(userGateway, userDataAccessObject)

open class UserCreationUserViewModelMock : UserViewModel(userRepository) {
    private val _userState = MutableLiveData<User>()
    override val userState: LiveData<User> = _userState
    private val _isInternetErrorRisenState = MutableLiveData(false)
    override val isInternetErrorRisenState: LiveData<Boolean> = _isInternetErrorRisenState
    private val _isUserUsernameInvalidState = MutableLiveData(false)
    override val isUserUsernameInvalidState: LiveData<Boolean> = _isUserUsernameInvalidState

    override fun verifyIfUserExists(onUserCreated: () -> Unit) {
    }

    override fun createUser(username: String, onUserCreated: () -> Unit) {
        if (username.isEmpty()) {
            _isUserUsernameInvalidState.value = true
        } else {
            onUserCreated()
        }
    }
}