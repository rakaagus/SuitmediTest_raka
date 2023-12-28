package com.raka.suitmediatest.data.remote

import com.raka.suitmediatest.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUser(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null,
    ): UserResponse
}