package com.miftah.mysubmissionintermediate.utils.fake

import com.miftah.mysubmissionintermediate.core.data.source.pref.UserPref
import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakePref : UserPref {
    private val dummyUser = mutableListOf<UserModel>()
    override suspend fun saveSession(user: UserModel) {
        val data = UserModel(
            username = user.username,
            userId = user.userId,
            token = user.token,
            isLogin = true
        )
        dummyUser.add(data)
    }

    override fun getSession(): Flow<UserModel> {
        val data = MutableSharedFlow<UserModel>()
        data.tryEmit(dummyUser[0])
        return data
    }

    override suspend fun logout() {
        dummyUser.removeAt(0)
    }

}