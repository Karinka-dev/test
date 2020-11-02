package com.shakuro.test.network

import com.shakuro.test.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository
@Inject
constructor(
    private val api: GitHabAPI
) {
    suspend fun getUsers(page: Int) = flow {
        try {
            emit(DataState.Loading)
            val resultList = api.getUsers(page).filterNotNull()
            emit(DataState.Success(resultList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}