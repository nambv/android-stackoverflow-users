package com.nambv.android_stackoverflow.service

import com.nambv.android_stackoverflow.data.remote.response.ReputationResponse
import com.nambv.android_stackoverflow.data.remote.response.UsersResponse
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
