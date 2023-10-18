package com.miftah.mysubmissionintermediate.core.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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

class AppRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun userLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.login(email, password)
            if (!client.error) {
                userPreference.saveSession(loginResultToModel(client.loginResult))
                emit(Result.Success(client))
            } else {
                emit(Result.Error(client.message))
            }
        } catch (e: Exception) {
            Log.d(TAG, "userLogin: ${e.message}")
            emit(Result.Error(e.message.toString()))
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
            if (client.error) emit(Result.Error(client.message)) else emit(Result.Success(client))
        } catch (e: Exception) {
            Log.d(TAG, "userLogin: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getSession() = userPreference.getSession()

    suspend fun removeSession() = userPreference.logout()

    fun getAllStories(token: String): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.getStories("Bearer $token")
            if (client.error) emit(Result.Error(client.message)) else emit(Result.Success(client.listStory))
        } catch (e: Exception) {
            Log.d(TAG, "userLogin: ${e.message}")
            emit(Result.Error(e.message.toString()))
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
            if (client.error) emit(Result.Error(client.message)) else emit(Result.Success(client))
        } catch (e: Exception) {
            Log.d(TAG, "userLogin: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        const val TAG = "AppRepositoryLog"
    }
}