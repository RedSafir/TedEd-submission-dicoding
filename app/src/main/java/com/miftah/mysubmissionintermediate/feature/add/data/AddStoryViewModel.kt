package com.miftah.mysubmissionintermediate.feature.add.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.miftah.mysubmissionintermediate.core.data.AppRepository

class AddStoryViewModel(private val repository: AppRepository) : ViewModel() {

    fun storedStory(token: String, description: String, uri: Uri, context: Context) =
        repository.storedStory(
            token,
            description,
            uri,
            context
        )
}