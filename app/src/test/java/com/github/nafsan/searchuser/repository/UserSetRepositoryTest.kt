package com.github.nafsan.searchuser.repository


import com.github.nafsan.searchuser.datastore.UserSetDataStore
import com.github.nafsan.searchuser.datastore.UserSetRemoteDataStore
import com.github.nafsan.searchuser.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class UserSetRepositoryTest {
    private val name = "a"
    private val page = 1
    private  val perpage = 10

    @Mock
    var localDataStore: UserSetDataStore? = null

    @Mock
    var remoteDataStore: UserSetRemoteDataStore? = null

    var userSetRepository: UserSetRepository? = null

    var userSets = mutableListOf<User>()


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        userSetRepository = UserSetRepository.instance.apply {
            init(remoteDataStore!!)
        }
    }



    @Test
    fun shouldCallGetUserFromRemote() {
        runBlocking {
            `when`(localDataStore?.getUser(name,page,perpage)).thenReturn(null)
            `when`(remoteDataStore?.getUser(name,page,perpage)).thenReturn(userSets)
            userSetRepository?.getUser(name,page,perpage)
            verify(remoteDataStore, times(1))?.getUser(name,page,perpage)


        }

    }

    @Test
    fun shouldThrowExceptionWhenRemoteThrowAnException() {
        runBlocking {
            `when`(localDataStore?.getUser(name,page,perpage)).thenReturn(null)
            `when`(remoteDataStore?.getUser(name,page,perpage)).thenAnswer{throw Exception()}
            try {
                userSetRepository?.getUser(name,page,perpage)
            }catch (ex:Exception){

            }
        }
    }
}