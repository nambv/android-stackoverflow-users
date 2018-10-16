package com.nambv.android_stackoverflow.service

import com.nambv.android_stackoverflow.data.remote.response.ReputationResponse
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.remote.response.UsersResponse
import com.nambv.android_stackoverflow.data.remote.ApiService
import com.nambv.android_stackoverflow.utils.Constants.SITE
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET(ApiEndpoint.API_USERS)
    fun fetchUsers(@Query("page") page: Int,
                   @Query("pagesize") pageSize: Int,
                   @Query("site") site: String): Single<Response<UsersResponse>>

    @GET(ApiEndpoint.API_REPUTATION_DETAIL)
    fun fetchReputationDetail(@Query("page") page: Int,
                              @Query("pagesize") pageSize: Int,
                              @Query("site") site: String): Single<Response<ReputationResponse>>
}

object UserService {

    private val api: UserApi = ApiService.apiClient.create(UserApi::class.java)

    fun fetchUsers(page: Int, pageSize: Int): Single<List<User>> {
        return api.fetchUsers(page, pageSize, SITE)
                .map { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {

                            return@map it.users
                        }
                    } else {
                        throw RuntimeException("Error when fetch users")
                    }
                }
    }
}