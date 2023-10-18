package com.miftah.mysubmissionintermediate.core.data.source.pref.model

data class UserModel(
    val username: String,
    val userId : String,
    val token: String,
    val isLogin: Boolean = false
)
