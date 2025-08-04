package com.example.truecallerassignment.presentation.ui

data class MainUiState(
    val isLoading: Boolean = false,
    val content: String? = null,
    val nthChar: String? = null,
    val allNthChar: String = "",
    val wordCounts: String = "",
    val errorMessage: String? = null,  // General Error (for content fetch)
    val nthCharError: String? = null,
    val allNthCharError: String? = null,
    val wordCountError: String? = null
)
