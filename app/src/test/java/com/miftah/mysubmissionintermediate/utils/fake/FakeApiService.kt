package com.miftah.mysubmissionintermediate.utils.fake

import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ResultResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.StoriesResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.retrofit.ApiService
import com.miftah.mysubmissionintermediate.utils.dataDummy.RemoteResponseDummy
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {
    override suspend fun login(email: String, password: String): LoginResponse {
        return RemoteResponseDummy.generateLoginResponse()
    }

    override suspend fun register(name: String, email: String, password: String): ResultResponse {
        return RemoteResponseDummy.generateStoriesResponseResult(false, "success")
    }

    override suspend fun getStories(page: Int, size: Int): StoriesResponse {
        return RemoteResponseDummy.generateStoriesResponse()
    }

    override suspend fun getStoriesWithMap(location: Int): StoriesResponse {
        return RemoteResponseDummy.generateStoriesResponse()
    }

    override suspend fun stored(
        file: MultipartBody.Part,
        description: RequestBody
    ): ResultResponse {
        return RemoteResponseDummy.generateStoriesResponseResult(false, "success")
    }

    override suspend fun storedWithLocation(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Float,
        lon: Float
    ): ResultResponse {
        TODO("Not yet implemented")
    }
}