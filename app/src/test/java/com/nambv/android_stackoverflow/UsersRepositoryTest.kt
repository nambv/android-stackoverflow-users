package com.nambv.android_stackoverflow

import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.local.dao.UserDao
import com.nambv.android_stackoverflow.data.remote.response.UsersResponse
import com.nambv.android_stackoverflow.repository.UsersRepository
import com.nambv.android_stackoverflow.service.UserApi
import com.nambv.android_stackoverflow.utils.Constants
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import retrofit2.Response

class UsersRepositoryTest {

    lateinit var userApi: UserApi
    lateinit var userDao: UserDao
    lateinit var usersResponse: Response<UsersResponse>
    lateinit var application: MainApplication

    private lateinit var userRepository: UsersRepository
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setup() {

        userApi = mock()
        userDao = mock()
        usersResponse = mock()

        `when`(userDao.getUserList(0, 0)).thenReturn(Single.just(emptyList()))
        userRepository = UsersRepository(application, userApi, userDao)
    }
//
//    @Test
//    fun test_emptyCache_hasDataOnApi_returnsApiData() {
//        `when`(userApi.getUsers()).thenReturn(Observable.just(listOf(aRandomUser())))
//
//        userRepository.getUsers().test()
//                .assertValueCount(1)
//                .assertValue { it.size == 1 }
//    }
}