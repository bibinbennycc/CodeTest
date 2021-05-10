package com.codetest.feature.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.codetest.common.Status
import com.codetest.getOrAwaitValueTest
import com.codetest.repository.MockRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: MockRepository

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        repository = MockRepository()
        viewModel = MainViewModel(repository, appContext)
    }

    @Test
    fun testGetLocationsReturnSuccess() = runBlockingTest {
        viewModel.getLocations()
        val result = viewModel.locationResponse.getOrAwaitValueTest()
        assertThat(result.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun testGetLocationsReturnError() = runBlockingTest {
        repository.returnErrorResponse = true
        viewModel.getLocations()
        val result = viewModel.locationResponse.getOrAwaitValueTest()
        assertThat(result.status).isEqualTo(Status.ERROR)
    }

}