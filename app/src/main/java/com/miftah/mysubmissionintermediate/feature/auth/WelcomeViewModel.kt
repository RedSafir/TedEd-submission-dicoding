package com.miftah.mysubmissionintermediate.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel
import kotlinx.coroutines.launch

class WelcomeViewModel(private val repository: AppRepository) : ViewModel() {

    fun userRegis(email: String, username: String, password: String) =
        repository.userRegis(name = username, email = email, password = password)

    fun userLogin(email: String, password: String) = repository.userLogin(email, password)

    fun saveSession(user : UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

}