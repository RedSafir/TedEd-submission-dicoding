package com.miftah.mysubmissionintermediate.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

    fun removeSession() {
        viewModelScope.launch {
            repository.removeSession()
        }
    }

    fun getAllStories() = repository.getAllStories().cachedIn(viewModelScope)
}