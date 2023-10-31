package com.miftah.mysubmissionintermediate.feature.gmaps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.Result
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ListStoryItem
import com.miftah.mysubmissionintermediate.utils.MainDispatcherRule
import com.miftah.mysubmissionintermediate.utils.dataDummy.RemoteResponseDummy
import com.miftah.mysubmissionintermediate.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsVieModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repository: AppRepository
    private lateinit var viewModel: MapsVieModel

    @Before
    fun setUp() {
        viewModel = MapsVieModel(repository)
    }

    @Test
    fun `when Get Story with LngLg should Not Null`() {
        val dummyData = RemoteResponseDummy.generateStoriesResponse()
        val expectedData = MutableLiveData<Result<List<ListStoryItem>>>()
        expectedData.value = Result.Success(dummyData.listStory)

        `when`(repository.getAllStoriesWithMap()).thenReturn(expectedData)
        val actualData = viewModel.getStoryWithLatLng().getOrAwaitValue()

        Mockito.verify(repository).getAllStoriesWithMap()
        Assert.assertTrue(actualData is Result.Success)
        Assert.assertEquals(dummyData.listStory.size, (actualData as Result.Success).data.size)
    }

    @Test
    fun `when Get Story with LngLg should empty and return Empty`() {
        val expectedData = MutableLiveData<Result<List<ListStoryItem>>>()
        expectedData.value = Result.Success(emptyList())

        `when`(repository.getAllStoriesWithMap()).thenReturn(expectedData)
        val actualData = viewModel.getStoryWithLatLng().getOrAwaitValue()

        Mockito.verify(repository).getAllStoriesWithMap()
        Assert.assertTrue(actualData is Result.Success)
        Assert.assertEquals(0, (actualData as Result.Success).data.size)
    }

}