package com.example.domain2.usecase.impl

import com.example.domain.repository.WebsiteRepository
import com.example.domain.usecase.contract.GetEveryNthCharUseCase
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
        try {
            emit(UiState.Loading)
            val blogContent = repository.fetchWebsiteText()
            emit(UiState.Success(blogContent))
        } catch (e: IOException) {
            emit(UiState.Failure(getErrorMessage(e)))
        } catch (e: HttpException) {
            emit(UiState.Failure("Something went wrong"))
        }   catch (e: ContentNotFoundException) {
            when (e.code) {
                400 ->  emit(UiState.Failure("Invalid request. Please try again."))
                401 -> emit(UiState.Failure("Unauthorized. Please log in again."))
                403 -> emit(UiState.Failure("Access denied."))
                404 -> emit(UiState.Failure("Data not found."))
                408 ->emit(UiState.Failure("Request timeout. Please retry."))
                429 -> emit(UiState.Failure("Too many requests. Please wait."))
                else -> emit(UiState.Failure("Unknown error. Code: ${e.code}"))
            }
        }
    }.flowOn(ioDispatcher)

    fun getErrorMessage(e: IOException): String {
        return when (e) {
            is UnknownHostException -> "No Internet connection. Please check your network."
            is SocketTimeoutException -> "The request timed out. Please try again."
            else -> "Network error occurred. Please try again."
        }
    }
}
