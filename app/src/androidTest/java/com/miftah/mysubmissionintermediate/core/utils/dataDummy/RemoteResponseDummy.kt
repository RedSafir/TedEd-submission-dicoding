package com.miftah.mysubmissionintermediate.utils.dataDummy

import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ListStoryItem
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResult
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ResultResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.StoriesResponse

object RemoteResponseDummy {
    fun generateStoriesResponse(): StoriesResponse {
        return StoriesResponse(
            listStory = generateListStoryItem(),
            error = false,
            message = "success"
        )
    }

    fun generateListStoryItem(): List<ListStoryItem> {
        val newList = ArrayList<ListStoryItem>()
        for (i in 1..15) {
            val listStoryItem = ListStoryItem(
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                name = "Miftah",
                description = "Text",
                lon = -16.002,
                id = "story-FvU4u0Vp2S3PMsFg",
                lat = -10.212
            )
            newList.add(listStoryItem)
        }
        return newList
    }

    fun generateStoriesResponseResult(err: Boolean, msg: String): ResultResponse {
        return ResultResponse(
            error = err,
            message = msg
        )
    }

    fun generateLoginResponse() : LoginResponse {
        return LoginResponse(
            loginResult = generateLoginResult(),
            error = false,
            message = "success"
        )
    }

    fun generateLoginResult() : LoginResult {
        return LoginResult(
            name = "Miftah",
            userId = "user-yj5pc_LARC_AgK61",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flEMaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I"
        )
    }
}