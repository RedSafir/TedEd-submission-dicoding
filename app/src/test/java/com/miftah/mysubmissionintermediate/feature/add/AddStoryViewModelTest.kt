package com.miftah.mysubmissionintermediate.feature.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repository: AppRepository
    private lateinit var viewModel: AddStoryViewModel

    @Before
    fun setUp() {
        viewModel = AddStoryViewModel(repository)
    }

    fun `When Stored Story without Location, Return Success`() {

    }
}