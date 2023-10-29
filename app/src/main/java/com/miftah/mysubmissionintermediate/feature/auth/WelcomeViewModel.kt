package com.miftah.mysubmissionintermediate.feature.auth

import androidx.lifecycle.ViewModel
import com.miftah.mysubmissionintermediate.core.data.AppRepository

class WelcomeViewModel(private val repository: AppRepository) : ViewModel() {

    fun userRegis(email: String, username: String, password: String) =
        repository.userRegis(name = username, email = email, password = password)

    fun userLogin(email: String, password: String) = repository.userLogin(email, password)

}