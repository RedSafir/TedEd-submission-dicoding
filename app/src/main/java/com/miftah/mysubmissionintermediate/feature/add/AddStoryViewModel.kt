package com.miftah.mysubmissionintermediate.feature.add

import android.content.Context
import android.location.Location
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.miftah.mysubmissionintermediate.core.data.AppRepository

class AddStoryViewModel(private val repository: AppRepository) : ViewModel() {

    fun storedStory(description: String, uri: Uri, context: Context) =
        repository.storedStory(
            description,
            uri,
            context
        )

    fun storedStoryLocation(location : Location, description: String, uri: Uri, context: Context) =
        repository.storedStoryWithLocation(
            location,
            description,
            uri,
            context
        )
}