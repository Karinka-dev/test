package com.shakuro.test

import retrofit2.http.GET
import retrofit2.http.Query

interface GitHabAPI {

    @GET("users")
    suspend fun getUsers(
        @Query("since") page: Int
    ): List<User?>
}
