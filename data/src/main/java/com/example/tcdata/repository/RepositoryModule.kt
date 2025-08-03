package com.example.tcdata.repository



import com.example.domain.repository.WebsiteRepository
import com.example.network2.api.TruecallerAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideWebsiteRepository(api: TruecallerAPI): WebsiteRepository =
       WebsiteRepositoryImpl(api)

}