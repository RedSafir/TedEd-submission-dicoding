package com.miftah.mysubmissionintermediate.feature.main.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

    fun removeSession() {
        viewModelScope.launch {
            repository.removeSession()
        }
    }

    fun getAllStories(token : String) = repository.getAllStories(token)

    fun saveToken(token: String) {
        _token.value = token
    }

    fun removeToken() {
        _token.value = ""
    }
}