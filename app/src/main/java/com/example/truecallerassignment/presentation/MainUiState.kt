package com.example.truecallerassignment.presentation


data class MainUiState(
    val isLoading: Boolean = false,
    val content: String? = null,
    val errorMessage: String? = null,
    val nthChar: String? = null,
    val allNthChar:String = "",
    val wordCounts: String = ""
)