package com.example.domain2.usecase.impl

import com.example.domain.repository.WebsiteRepository
import com.example.domain2.IoDispatcher
import com.example.domain2.UiState
import com.example.domain2.usecase.contract.FetchContentUsecase
import com.example.network2.api.error.ContentNotFoundException
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class FetchContentUsecaseImpl @Inject constructor(
    private val repository: WebsiteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FetchContentUsecase {
    override fun invoke() = flow {
        emit(UiState.Loading)
        try {
            val blogContent = repository.fetchWebsiteText()
            emit(UiState.Success(blogContent))
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is IOException -> getErrorMessage(e)
                is HttpException -> "Something went wrong"
                is ContentNotFoundException -> when (e.code) {
                    400 -> "Invalid request. Please try again."
                    401 -> "Unauthorized. Please log in again."
                    403 -> "Access denied."
                    404 -> "Data not found."
                    408 -> "Request timeout. Please retry."
                    429 -> "Too many requests. Please wait."
                    else -> "Unknown error. Code: ${e.code}"
                }

                else -> "Unknown error: ${e.localizedMessage ?: e}"
            }
            emit(UiState.Failure(errorMessage))
        }
    }.flowOn(ioDispatcher)

    private fun getErrorMessage(e: IOException): String {
    return when (e) {
        is UnknownHostException -> "No Internet connection. Please check your network."
        is SocketTimeoutException -> "The request timed out. Please try again."
        else -> "Network error occurred. Please try again."
    }
}
}
