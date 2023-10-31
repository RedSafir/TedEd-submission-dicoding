package com.miftah.mysubmissionintermediate.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.source.local.entity.Stories
import com.miftah.mysubmissionintermediate.core.ui.AdapterCardStories
import com.miftah.mysubmissionintermediate.utils.MainDispatcherRule
import com.miftah.mysubmissionintermediate.utils.dataDummy.LocalEntityDummy
import com.miftah.mysubmissionintermediate.utils.dataDummy.PreffUserDummy
import com.miftah.mysubmissionintermediate.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repository: AppRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val dummyStories = LocalEntityDummy.generateStories()
        val data: PagingData<Stories> = PagingData.from(dummyStories)
        val expectedData = MutableLiveData<PagingData<Stories>>()
        expectedData.value = data

        `when`(repository.getAllStories()).thenReturn(expectedData)
        val actualData: PagingData<Stories> = viewModel.getAllStories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = AdapterCardStories.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualData)

        Mockito.verify(repository).getAllStories()
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStories.size, differ.snapshot().size)
        Assert.assertEquals(dummyStories[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Should Empty and Return No Data`() = runTest {
        val data: PagingData<Stories> = PagingData.from(emptyList())
        val expectedData = MutableLiveData<PagingData<Stories>>()
        expectedData.value = data

        `when`(repository.getAllStories()).thenReturn(expectedData)
        val actualData: PagingData<Stories> = viewModel.getAllStories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = AdapterCardStories.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualData)

        Mockito.verify(repository).getAllStories()
        Assert.assertEquals(0, differ.snapshot().size)
    }

    @Test
    fun `when Get Session Should Get Data`() {
        val sampleUser = flowOf(PreffUserDummy.generateUser(true))

        `when`(repository.getSession()).thenReturn(sampleUser)
        val actualData = viewModel.getSession().getOrAwaitValue()

        Mockito.verify(repository).getSession()
        Assert.assertNotNull(actualData)
        Assert.assertEquals(sampleUser.asLiveData().getOrAwaitValue(), actualData)
    }

    @Test
    fun `when Remove Session Should DataStore Removed`() = runTest {
        `when`(repository.removeSession()).thenReturn(Unit)
        viewModel.removeSession()

        Mockito.verify(repository).removeSession()
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}