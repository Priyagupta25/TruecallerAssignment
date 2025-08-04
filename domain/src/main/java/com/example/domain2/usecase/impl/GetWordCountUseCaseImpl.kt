package com.example.domain.usecase.impl

import com.example.domain.repository.WebsiteRepository
import com.example.domain.usecase.contract.GetWordCountUseCase
import com.example.domain2.DefaultDispatcher
import com.example.domain2.IoDispatcher
import com.example.domain2.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class GetWordCountUseCaseImpl  @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : GetWordCountUseCase {


    override fun invoke(text :String) = flow {
        try {
            emit(UiState.Loading)
            val map = getWordCountMapFromBlogContent(text)
            emit(UiState.Success(getWordCountStringFromMap(map)))
        } catch (e: HttpException) {
            emit(UiState.Failure("Something went wrong"))
        } catch (e: IOException) {
            emit(UiState.Failure(e.message))
        }
    }.flowOn(defaultDispatcher)

    private fun getWordCountMapFromBlogContent(text: String) =
    text
    .split("\\s+".toRegex())
    .filter { it.isNotBlank() }      // Filter out empty strings
    .map { it.lowercase() }          // Convert all words to lowercase
    .groupingBy { it }
    .eachCount()

    private fun getWordCountStringFromMap(map: Map<String, Int>) = buildString {
        map.map {
            append("${it.key}: ${it.value}")
            append("\n")
        }
    }


}