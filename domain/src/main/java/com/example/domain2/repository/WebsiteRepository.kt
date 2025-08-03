package com.example.domain.repository

interface WebsiteRepository {
    suspend fun fetchWebsiteText(): String
}