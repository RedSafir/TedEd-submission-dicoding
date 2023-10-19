package com.miftah.mysubmissionintermediate.core.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.miftah.mysubmissionintermediate.core.data.source.pref.UserPreference
import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ListStoryItem
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResult
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ResultResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.retrofit.ApiService
import com.miftah.mysubmissionintermediate.core.utils.reduceFileImage
import com.miftah.mysubmissionintermediate.core.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class AppRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun userLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.login(email, password)
            userPreference.saveSession(loginResultToModel(client.loginResult))
            emit(Result.Success(client))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ResultResponse::class.java)
            val errorMessage = errorBody.message
            Log.d(TAG, "userLogin: $errorMessage")
            emit(Result.Error(errorMessage))
        }
    }

    private fun loginResultToModel(loginResult: LoginResult) = UserModel(
        username = loginResult.name,
        userId = loginResult.userId,
        token = loginResult.token
    )

    fun userRegis(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<ResultResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.register(name = name, email = email, password = password)
            emit(Result.Success(client))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ResultResponse::class.java)
            val errorMessage = errorBody.message
            Log.d(TAG, "userLogin: $errorMessage")
            emit(Result.Error(errorMessage))
        }
    }

    fun getSession() = userPreference.getSession()

    suspend fun removeSession() = userPreference.logout()

    fun getAllStories(token: String): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.getStories("Bearer $token")
            emit(Result.Success(client.listStory))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ResultResponse::class.java)
            val errorMessage = errorBody.message
            Log.d(TAG, "userLogin: $errorMessage")
            emit(Result.Error(errorMessage))
        }
    }

    fun storedStory(
        token: String,
        description: String,
        uri: Uri,
        context: Context
    ): LiveData<Result<ResultResponse>> = liveData {
        emit(Result.Loading)
        val imageFile = uriToFile(uri, context).reduceFileImage()
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val client = apiService.stored("Bearer $token", multipartBody, requestBody)
            emit(Result.Success(client))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ResultResponse::class.java)
            val errorMessage = errorBody.message
            Log.d(TAG, "userLogin: $errorMessage")
            emit(Result.Error(errorMessage))
        }
    }

    companion object {
        const val TAG = "AppRepositoryLog"
    }
}