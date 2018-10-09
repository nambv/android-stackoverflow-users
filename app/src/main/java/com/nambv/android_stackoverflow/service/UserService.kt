package com.nambv.android_stackoverflow.service

import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.UsersResponse
import com.nambv.android_stackoverflow.service.ApiService.Factory.retrofitBuilder
import com.nambv.android_stackoverflow.utils.Constants.SITE
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET(ApiEndpoint.USERS)
    fun fetchUsers(@Query("page") page: Int,
                   @Query("pagesize") pageSize: Int,
                   @Query("site") site: String): Single<Response<UsersResponse>>
}

object UserService {

    private val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request())
                        .newBuilder()
                        .header("Accept", "application/json;charset=utf-8")
                        .header("Accept-Language", "en")
                        .build()
            }
            .build()

    private val loggingClient = OkHttpClient.Builder()
            .addInterceptor(
                    HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private val apiClient: Retrofit = retrofitBuilder
            .baseUrl(ApiEndpoint.BASE_URL)
            .client(httpClient)
            .client(loggingClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val api: UserApi = apiClient.create(UserApi::class.java)

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