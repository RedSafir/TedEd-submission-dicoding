package com.miftah.mysubmissionintermediate.core.utils

import com.miftah.mysubmissionintermediate.core.data.source.local.entity.Stories
import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ListStoryItem
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResult


fun ListStoryItem.listStoryToStories(): Stories {
    return Stories(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        lat = lat,
        lon = lon
    )
}

fun LoginResult.loginResultToUserModel(): UserModel {
    return UserModel(
        username = name,
        userId = userId,
        token = token
    )
}