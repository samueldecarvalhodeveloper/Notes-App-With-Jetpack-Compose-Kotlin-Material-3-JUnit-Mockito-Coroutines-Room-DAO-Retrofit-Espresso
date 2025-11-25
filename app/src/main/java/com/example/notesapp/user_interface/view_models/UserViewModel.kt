package com.example.notesapp.user_interface.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.external_models.User
import com.example.notesapp.data.repositories.UserRepository
import kotlinx.coroutines.launch

open class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userState = MutableLiveData<User>()
    open val userState: LiveData<User> = _userState
    private val _isInternetErrorRisenState = MutableLiveData(false)
    open val isInternetErrorRisenState: LiveData<Boolean> = _isInternetErrorRisenState
    private val _isUserUsernameInvalidState = MutableLiveData(false)
    open val isUserUsernameInvalidState: LiveData<Boolean> = _isUserUsernameInvalidState

    open fun verifyIfUserExists(onUserCreated: () -> Unit) {
        viewModelScope.launch {
            try {
                val userFromDatabase = userRepository.getUser()

                _userState.value = userFromDatabase

                onUserCreated()
            } catch (_: Exception) {
            }
        }
    }

    open fun createUser(username: String, onUserCreated: () -> Unit) {
        if (username.isEmpty()) {
            _isUserUsernameInvalidState.value = true
        } else {
            _isUserUsernameInvalidState.value = false

            viewModelScope.launch {
                try {
                    val createdUserOnService = userRepository.getCreatedUser(username)

                    _userState.value = createdUserOnService

                    onUserCreated()
                } catch (_: Exception) {
                    _isInternetErrorRisenState.value = true
                }
            }
        }
    }
}
