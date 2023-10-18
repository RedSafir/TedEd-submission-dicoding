package com.miftah.mysubmissionintermediate.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.di.Injection
import com.miftah.mysubmissionintermediate.feature.add.data.AddStoryViewModel
import com.miftah.mysubmissionintermediate.feature.auth.data.WelcomeViewModel
import com.miftah.mysubmissionintermediate.feature.main.data.MainActivityViewModel

class ViewModelFactory private constructor(private val appRepository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(appRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }


    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ViewModelFactory(
                            Injection.provideRepository(
                                context
                            )
                        )
                }
    }
}