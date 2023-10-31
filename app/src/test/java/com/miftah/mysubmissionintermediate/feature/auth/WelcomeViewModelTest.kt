package com.miftah.mysubmissionintermediate.feature.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.miftah.mysubmissionintermediate.core.data.AppRepository
import com.miftah.mysubmissionintermediate.core.data.Result
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.LoginResponse
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ResultResponse
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
class WelcomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AppRepository
    private lateinit var viewModel: WelcomeViewModel

    @Before
    fun setUp() {
        viewModel = WelcomeViewModel(repository)
    }

    @Test
    fun `when Input Username, Password, Email, Registration Success`() {
        val dummyData = RemoteResponseDummy.generateStoriesResponseResult(false, "success")
        val expectedData = MutableLiveData<Result<ResultResponse>>()
        expectedData.value = Result.Success(dummyData)

        `when`(repository.userRegis(name = "Miftah", email = "miftah@gmail.com", password = "12345678")).thenReturn(expectedData)
        val actualData = viewModel.userRegis(email = "miftah@gmail.com", username = "Miftah" , password = "12345678").getOrAwaitValue()

        Mockito.verify(repository).userRegis("Miftah", "miftah@gmail.com", "12345678")
        Assert.assertNotNull(actualData)
        Assert.assertTrue(actualData is Result.Success)
        Assert.assertEquals(dummyData.error, (actualData as Result.Success).data.error)
    }

    @Test
    fun `when Input Password less than 8, Registration Failed`() {
        val dummyData = RemoteResponseDummy.generateStoriesResponseResult(true, "err")
        val expectedData = MutableLiveData<Result<ResultResponse>>()
        expectedData.value = Result.Error(dummyData.message)

        `when`(repository.userRegis(name = "Miftah", email = "miftah@gmail.com", password = "1")).thenReturn(expectedData)
        val actualData = viewModel.userRegis(email = "miftah@gmail.com", username = "Miftah" , password = "1").getOrAwaitValue()

        Mockito.verify(repository).userRegis("Miftah", "miftah@gmail.com", "1")
        Assert.assertNotNull(actualData)
        Assert.assertTrue(actualData is Result.Error)
        Assert.assertEquals(dummyData.message, (actualData as Result.Error).error)
    }

    @Test
    fun `when Input Email already in Used, Registration Failed`() {
        val dummyData = RemoteResponseDummy.generateStoriesResponseResult(true, "Password must be at least 8 characters long")
        val expectedData = MutableLiveData<Result<ResultResponse>>()
        expectedData.value = Result.Error(dummyData.message)

        `when`(repository.userRegis(name = "Miftah", email = "miftah@gmail.com", password = "12345678")).thenReturn(expectedData)
        val actualData = viewModel.userRegis(email = "miftah@gmail.com", username = "Miftah" , password = "12345678").getOrAwaitValue()

        Mockito.verify(repository).userRegis("Miftah", "miftah@gmail.com", "12345678")
        Assert.assertNotNull(actualData)
        Assert.assertTrue(actualData is Result.Error)
        Assert.assertEquals(dummyData.message, (actualData as Result.Error).error)
    }

    @Test
    fun `when Input not Email, Registration Failed`() {
        val dummyData = RemoteResponseDummy.generateStoriesResponseResult(true, "\\\"email\\\" must be a valid email")
        val expectedData = MutableLiveData<Result<ResultResponse>>()
        expectedData.value = Result.Error(dummyData.message)

        `when`(repository.userRegis(name = "Miftah", email = "not_email", password = "12345678")).thenReturn(expectedData)
        val actualData = viewModel.userRegis(email = "not_email", username = "Miftah" , password = "12345678").getOrAwaitValue()

        Mockito.verify(repository).userRegis("Miftah", "not_email", "12345678")
        Assert.assertNotNull(actualData)
        Assert.assertTrue(actualData is Result.Error)
        Assert.assertEquals(dummyData.message, (actualData as Result.Error).error)
    }

    @Test
    fun `when Input Email and Password, Login Success`() {
        val dummyData = RemoteResponseDummy.generateLoginResponse()
        val expectedData = MutableLiveData<Result<LoginResponse>>()
        expectedData.value = Result.Success(dummyData)

        `when`(repository.userLogin(email = "miftah@gmail.com", password = "12345678")).thenReturn(expectedData)
        val actualData = viewModel.userLogin(email = "miftah@gmail.com", password = "12345678").getOrAwaitValue()

        Mockito.verify(repository).userLogin(email = "miftah@gmail.com", password = "12345678")
        Assert.assertNotNull(actualData)
        Assert.assertTrue(actualData is Result.Success)
        Assert.assertEquals(dummyData.error, (actualData as Result.Success).data.error)
    }

    @Test
    fun `when Input Wrong Email, Login Failed`() {
        val dummyData = RemoteResponseDummy.generateLoginResponse()
        val expectedData = MutableLiveData<Result<LoginResponse>>()
        expectedData.value = Result.Error(dummyData.message)

        `when`(repository.userLogin(email = "wrong_email", password = "12345678")).thenReturn(expectedData)
        val actualData = viewModel.userLogin(email = "wrong_email", password = "12345678").getOrAwaitValue()

        Mockito.verify(repository).userLogin(email = "wrong_email", password = "12345678")
        Assert.assertNotNull(actualData)
        Assert.assertTrue(actualData is Result.Error)
        Assert.assertEquals(dummyData.message, (actualData as Result.Error).error)
    }

    @Test
    fun `when Input Wrong Password, Login Failed`() {
        val dummyData = RemoteResponseDummy.generateLoginResponse()
        val expectedData = MutableLiveData<Result<LoginResponse>>()
        expectedData.value = Result.Error(dummyData.message)

        `when`(repository.userLogin(email = "miftah@gmail.com", password = "1234567810")).thenReturn(expectedData)
        val actualData = viewModel.userLogin(email = "miftah@gmail.com", password = "1234567810").getOrAwaitValue()

        Mockito.verify(repository).userLogin(email = "miftah@gmail.com", password = "1234567810")
        Assert.assertNotNull(actualData)
        Assert.assertTrue(actualData is Result.Error)
        Assert.assertEquals(dummyData.message, (actualData as Result.Error).error)
    }
}