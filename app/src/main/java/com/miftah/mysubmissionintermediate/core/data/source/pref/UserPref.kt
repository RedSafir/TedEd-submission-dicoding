package com.miftah.mysubmissionintermediate.core.data.source.pref

import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserPref {
    suspend fun saveSession(user: UserModel)

    fun getSession(): Flow<UserModel>

    suspend fun logout()
}