package com.shakuro.test.network

import com.shakuro.test.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHabAPI {

    @GET("users")
    suspend fun getUsers(
        @Query("since") page: Int
    ): List<User?>
}
