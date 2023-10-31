package com.miftah.mysubmissionintermediate.feature.gmaps

import androidx.lifecycle.ViewModel
import com.miftah.mysubmissionintermediate.core.data.AppRepository

class MapsVieModel(private val repository: AppRepository) : ViewModel() {
    fun getStoryWithLatLng() = repository.getAllStoriesWithMap()

}