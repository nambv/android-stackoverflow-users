package com.nambv.android_stackoverflow

import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.local.dao.UserDao
import com.nambv.android_stackoverflow.data.remote.response.UsersResponse
import com.nambv.android_stackoverflow.repository.UsersRepository
import com.nambv.android_stackoverflow.service.UserApi
import com.nambv.android_stackoverflow.utils.BaseScheduler
import com.nambv.android_stackoverflow.utils.Constants
import com.nambv.android_stackoverflow.utils.Constants.PAGE_SIZE
import com.nambv.android_stackoverflow.utils.Constants.SITE
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UsersRepositoryTest {

    @Mock
    lateinit var userApi: UserApi

    @Mock
    lateinit var userDao: UserDao

    @Mock
    private lateinit var scheduler: BaseScheduler

    private lateinit var userRepository: UsersRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when` (scheduler.io()).thenReturn(Schedulers.trampoline())
        `when` (scheduler.ui()).thenReturn(Schedulers.trampoline())
        userRepository = UsersRepository.getInstance(userApi, userDao, scheduler)
    }

    @Test
    fun test_getUsersFromNetwork() {

        val page = 1

        `when` (userDao.getUserList(PAGE_SIZE, 0)).thenReturn(Single.just(getTestUsers()))
        `when` (userApi.fetchUsers(page, PAGE_SIZE, SITE)).thenReturn(getTestUsersResponseSingle())
        doNothing().`when`(userDao).insert(getTestUsers())

        val users = userRepository.fetchUsers(1, 30, null)
        users.test().assertResult(getTestUsers())

        verify(userDao).getUserList(PAGE_SIZE, 0)
        verify(userApi).fetchUsers(page, PAGE_SIZE, Constants.SITE)
        verify(userDao).insert(getTestUsers())
        verify(scheduler)
    }

    companion object {

        fun getTestUsersResponseSingle(): Single<UsersResponse>? {
            return Single.just(getUsersResponse(getTestUsers()))
        }

        fun getTestUsers(): List<User> {
            val users = mutableListOf<User>()
            for (i in 0..29) { users.add(getTestUser()) }
            return users.toList()
        }

        private fun getTestUser() = User(userId = 1, displayName = "Nam Bui Vu", bookmarked = null)

        private fun getUsersResponse(users: List<User>): UsersResponse {
            return UsersResponse(users = users, hasMore = true, quotaMax = 1000, quotaRemaining = 970)
        }
    }
}