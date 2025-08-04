package com.example.tcdata.repository

import com.example.domain.repository.WebsiteRepository
import com.example.network2.api.TruecallerAPI
import com.example.network2.api.error.ContentNotFoundException


import jakarta.inject.Inject

class WebsiteRepositoryImpl  @Inject constructor(
    private val trueCallerAPI: TruecallerAPI
): WebsiteRepository {
    override suspend fun fetchWebsiteText(): String {
        val response = trueCallerAPI.fetchWebContent()
        return if (response.isSuccessful) {
            response.body()?.trim()
                ?: throw ContentNotFoundException("Empty response body", response.code())
        } else {
            throw ContentNotFoundException(response.message(), response.code())
        }
    }
}