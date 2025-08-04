package com.example.domain.usecase.impl

import com.example.domain.usecase.contract.GetEveryNthCharUseCase
import com.example.domain2.DefaultDispatcher
import com.example.domain2.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class GetEveryNthCharUseCaseImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : GetEveryNthCharUseCase {
    override fun invoke(n: Int,text:String) = flow {
        try {
            emit(UiState.Loading)
            emit(UiState.Success(getEveryNthCharFromContent(text,n)))
        } catch (e: Exception) {
            emit(UiState.Failure("Unknown error: ${e.localizedMessage ?: e}"))
        }
    }.flowOn(defaultDispatcher)

    private fun getEveryNthCharFromContent(text: String, n: Int): String {
        if (n <= 0 || n > text.length) return ""
        return (n - 1 until text.length step n)
            .joinToString(",") { text[it].toString() }
    }

}