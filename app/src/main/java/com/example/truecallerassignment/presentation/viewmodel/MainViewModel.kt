package com.example.truecallerassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.contract.GetNthCharUseCase
import com.example.domain.usecase.contract.GetEveryNthCharUseCase
import com.example.domain.usecase.contract.GetWordCountUseCase
import com.example.domain2.UiState
import com.example.domain2.usecase.contract.FetchContentUsecase
import com.example.truecallerassignment.presentation.Constants.NthItem
import com.example.truecallerassignment.presentation.ui.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNthCharUseCase: GetNthCharUseCase,
    private val getAllNthCharUseCase: GetEveryNthCharUseCase,
    private val getWordCounterUseCase: GetWordCountUseCase,
    private val fetchContentUsecase: FetchContentUsecase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private fun fetchData(text: String) = viewModelScope.launch {
        supervisorScope {
            launch { getNthChar(NthItem, text) }
            launch { getAllNthChar(NthItem, text) }
            launch { getWordCounter(text) }
        }
    }
    fun fetchContentUsecase(n: Int) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                isLoading = true,
                content = null,
                errorMessage = null,
                nthChar = null,
                allNthChar = "",
                wordCounts = ""
            )
        }
        fetchContentUsecase().collect{ result ->
            if( result is UiState.Success){
                fetchData(result.data)
            }
            _uiState.update {
                when (result) {
                    is UiState.Loading -> it.copy(
                        content = "loading..",
                        errorMessage = null
                    )

                    is UiState.Success -> it.copy(
                        isLoading = false,
                        content = result.data)

                    is UiState.Failure -> it.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage,
                        content = ""
                    )
                }
            }
        }
    }

    fun getNthChar(n: Int,text: String) = viewModelScope.launch {
        getNthCharUseCase(n,text).collect { result ->
            _uiState.update {
                when (result) {
                    is UiState.Loading -> it.copy(
                        nthChar = "loading..",
                        nthCharError = null
                    )
                    is UiState.Success -> it.copy(nthChar = result.data)
                    is UiState.Failure -> it.copy(
                        nthChar = "",
                        nthCharError = result.errorMessage
                    )
                }
            }
        }
    }

    fun getWordCounter(text: String) = viewModelScope.launch {
        getWordCounterUseCase(text).collect { result ->
            _uiState.update {
                when (result) {
                    is UiState.Loading -> it.copy(
                        wordCounts = "loading..",
                        wordCountError = null
                    )

                    is UiState.Success -> it.copy(wordCounts = result.data)

                    is UiState.Failure -> it.copy(
                        wordCounts = "",
                        wordCountError = result.errorMessage
                    )
                }
            }
        }
    }

    fun getAllNthChar(n: Int,text: String) = viewModelScope.launch {
        getAllNthCharUseCase(n,text).collect { result ->
            _uiState.update {
                when (result) {
                    is UiState.Loading -> it.copy(
                        allNthChar = "loading..",
                        allNthCharError = null
                    )

                    is UiState.Success -> it.copy(
                        allNthChar = result.data,
                        allNthCharError = null
                    )

                    is UiState.Failure -> it.copy(
                        allNthChar = "",
                        allNthCharError = result.errorMessage
                    )
                }
            }
        }
    }


}