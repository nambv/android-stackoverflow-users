package com.nambv.android_stackoverflow.service

import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.UsersResponse
import com.nambv.android_stackoverflow.service.ApiService.Factory.retrofitBuilder
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface UserApi {

    @GET(ApiEndpoint.USERS)
    fun fetchUsers(): Single<Response<UsersResponse>>
}

object UserService {

    private val apiClient: Retrofit = retrofitBuilder.baseUrl(ApiEndpoint.BASE_URL).build()

    private val api: UserApi = apiClient.create(UserApi::class.java)

    fun getApiClient(): Retrofit = apiClient

    fun fetchUsers(): Single<List<User>> {
        return api.fetchUsers()
                .map { response ->
                    if (response.isSuccessful) {
                        response.body()?.let { return@map it.users }
                    } else {
                        throw RuntimeException("Error when fetch users")
                    }
                }
    }
}