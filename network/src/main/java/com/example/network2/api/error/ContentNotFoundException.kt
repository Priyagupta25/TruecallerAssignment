package com.example.network2.api.error

// exception when content is not found

data class ContentNotFoundException(val error: String, val code: Int) : RuntimeException(error)