package com.miftah.mysubmissionintermediate.core.di

import android.content.Context
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.source.local.room.StoryDatabase
import com.miftah.mysubmissionintermediate.core.data.source.pref.UserPreference
import com.miftah.mysubmissionintermediate.core.data.source.pref.dataStore
import com.miftah.mysubmissionintermediate.core.data.source.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): AppRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking{ pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val dB = StoryDatabase.getDatabase(context)
        return AppRepository(apiService, pref, dB)
    }
}