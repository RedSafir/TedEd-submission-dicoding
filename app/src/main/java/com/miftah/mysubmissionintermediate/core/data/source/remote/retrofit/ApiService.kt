package com.miftah.mysubmissionintermediate.core.data.source.remote.retrofit

import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ResultResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResultResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
    ): StoriesResponse

    @Multipart
    @POST("stories")
    suspend fun stored(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): ResultResponse
}