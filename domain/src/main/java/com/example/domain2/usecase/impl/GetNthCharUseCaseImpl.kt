package com.example.domain.usecase.impl

import com.example.domain.repository.WebsiteRepository
import com.example.domain.usecase.contract.GetNthCharUseCase
import com.example.domain2.IoDispatcher
import com.example.domain2.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

import java.io.IOException

class GetNthCharUseCaseImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetNthCharUseCase {
    override fun invoke(n: Int,text:String) = flow {
        try {
            emit(UiState.Loading)
            val nThChar = getNthChar(text,n)
            emit(UiState.Success(nThChar))
        } catch (e: HttpException) {
            emit(UiState.Failure("Something went wrong"))
        } catch (e: IOException) {
            emit(UiState.Failure(e.message))
        }
    }.flowOn(ioDispatcher)



    private fun getNthChar(text: String,n: Int) =
        text[n.minus(1)].toString()
}