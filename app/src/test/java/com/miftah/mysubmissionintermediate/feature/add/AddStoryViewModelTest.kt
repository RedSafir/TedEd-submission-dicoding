package com.miftah.mysubmissionintermediate.feature.add

import android.content.Context
import android.location.Location
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AppRepository
    @Mock
    private lateinit var mockContext: Context
    private lateinit var viewModel: AddStoryViewModel
    private lateinit var dummyUri : Uri
    private lateinit var dummyDescription: String
    private lateinit var dummyLocation: Location

    @Before
    fun setUp() {
        viewModel = AddStoryViewModel(repository)
        mockContext = mock(Context::class.java)
    }

/*    fun `When Stored Story without Location, Return Success`() {
        dummyUri = StoryDummy.imageUriBase64Dummy.base64ToUri(mockContext) as Uri
        dummyDescription = StoryDummy.descriptionDummy
        val dummyData = RemoteResponseDummy.generateStoriesResponseResult(false, "success")
        val expectedResult = MutableLiveData<Result<ResultResponse>>()
        expectedResult.value = Result.Success(dummyData)

        `when`(repository.storedStory(dummyDescription, dummyUri, mockContext)).thenReturn(expectedResult)
        val actualResult = viewModel.storedStory(dummyDescription, dummyUri, mockContext).getOrAwaitValue()

        Mockito.verify(repository).storedStory(dummyDescription, dummyUri, mockContext)

        Assert.assertTrue(actualResult is Result.Success)
        Assert.assertEquals(dummyData.error, (actualResult as Result.Success).data.error)
    }*/
}