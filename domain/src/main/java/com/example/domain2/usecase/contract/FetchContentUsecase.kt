package com.example.domain2.usecase.contract

import com.example.domain2.UiState
import kotlinx.coroutines.flow.Flow

interface FetchContentUsecase {
    operator fun invoke(): Flow<UiState<String>>
}