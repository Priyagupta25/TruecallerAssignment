package com.example.domain.usecase.contract



import com.example.domain2.UiState
import kotlinx.coroutines.flow.Flow

interface GetNthCharUseCase {
    operator fun invoke(n: Int,text:String): Flow<UiState<String>>
}