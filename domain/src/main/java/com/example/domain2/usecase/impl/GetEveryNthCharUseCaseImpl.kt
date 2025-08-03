package com.example.domain.usecase.impl

import com.example.domain.repository.WebsiteRepository
import com.example.domain.usecase.contract.GetEveryNthCharUseCase
import com.example.domain2.IoDispatcher
import com.example.domain2.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class GetEveryNthCharUseCaseImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetEveryNthCharUseCase {
    override fun invoke(n: Int,text:String) = flow {
        try {
            emit(UiState.Loading)
            emit(UiState.Success(getEveryNthCharFromBlogContent(text,n)))
        } catch (e: HttpException) {
            emit(UiState.Failure("Something went wrong"))
        } catch (e: IOException) {
            emit(UiState.Failure(e.message))
        }
    }.flowOn(ioDispatcher)

    private fun getEveryNthCharFromBlogContent(blogContent: String, n: Int) = buildString {
        for (i in n.minus(1)..blogContent.length step n) {
            append(blogContent[i].plus(","))
        }
        deleteCharAt(this.length.minus(1))
    }

}