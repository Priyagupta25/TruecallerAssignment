package com.example.domain.usecase.contract


import com.example.domain2.UiState
import kotlinx.coroutines.flow.Flow

interface GetWordCountUseCase {
    operator fun invoke(text:String): Flow<UiState<String>>

}