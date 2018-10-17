package com.nambv.android_stackoverflow

import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.local.dao.UserDao
import com.nambv.android_stackoverflow.data.remote.response.UsersResponse
import com.nambv.android_stackoverflow.repository.UsersRepository
import com.nambv.android_stackoverflow.service.UserApi
import com.nambv.android_stackoverflow.utils.Constants
import com.nhaarman.mockito_kotlin.doNothing
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.*

@RunWith(JUnit4::class)
class UsersRepositoryTest {

    @Mock
    lateinit var userApi: UserApi

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var usersResponse: Response<UsersResponse>

    @Mock
    lateinit var application: MainApplication

    private lateinit var userRepository: UsersRepository
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        userRepository = UsersRepository.getInstance(application, userApi, userDao)
    }

    @Test
    fun test_getUsersFromNetwork() {

        `when` (userDao.getUserList(0, 30)).thenReturn(null)
        `when` (userApi.fetchUsers(0, 30, Constants.SITE)).thenReturn(getTestUsersResponseSingle())
        doNothing().`when`(userDao).insert(getTestUsers())

        userRepository.fetchUsers(0, 30, null)
    }

    companion object {

        fun getTestUsersResponseSingle(): Single<UsersResponse>? {
            return Single.just(getUsersResponse(getTestUsers()))
        }

        fun getTestUsers(): List<User> {
            val users = mutableListOf<User>()
            for (i in 0..30) { users.add(getTestUser()) }
            return users.toList()
        }

        private fun getTestUser() = User(userId = Random().nextInt(), displayName = "Nam Bui Vu", bookmarked = null)

        private fun getUsersResponse(users: List<User>): UsersResponse {
            return UsersResponse(users = users, hasMore = true, quotaMax = 1000, quotaRemaining = 970)
        }
    }
}