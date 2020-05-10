package com.github.nafsan.searchuser.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.nafsan.searchuser.repository.UserSetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    private val name = "a"
    private val page = 1
    private val perpage = 10

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    var userSetRepository: UserSetRepository? = null

    var mainViewModel: MainvViewModel? = null

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainvViewModel(userSetRepository!!)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @Test
    fun shouldStopLoadingAndGiveDataWhenSuccess() {
        runBlocking {
            Mockito.`when`(userSetRepository?.getUser(name, page, perpage)).thenReturn(mutableListOf())
            mainViewModel?.getSets(name, page, perpage)
            val data = mainViewModel!!.data.value
            val loading = mainViewModel!!.loading.value

            Assert.assertNotNull(data)
            Assert.assertFalse(loading!!)
        }
    }

    @Test
    fun shouldThrowErrorWhenRepositoryIsThrowingError() {
        runBlocking {
            Mockito.`when`(userSetRepository?.getUser(name, page, perpage)).thenAnswer { throw Exception() }
            mainViewModel?.getSets(name, page, perpage)
            val error = mainViewModel!!.error.value
            val loading = mainViewModel!!.loading.value
            Assert.assertNotNull(error)
            Assert.assertFalse(loading!!)
        }
    }
}