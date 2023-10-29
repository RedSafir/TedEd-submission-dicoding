package com.miftah.mysubmissionintermediate.core.di

import android.content.Context
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.source.local.room.StoryDatabase
import com.miftah.mysubmissionintermediate.core.data.source.pref.UserPreference
import com.miftah.mysubmissionintermediate.core.data.source.pref.dataStore
import com.miftah.mysubmissionintermediate.core.data.source.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context : Context) : AppRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiConfig = ApiConfig.getApiService()
        val dB = StoryDatabase.getDatabase(context)
        return AppRepository(apiConfig, pref, dB)
    }
}