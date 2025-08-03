package com.example.domain2

import com.example.domain.repository.WebsiteRepository
import com.example.domain.usecase.contract.GetEveryNthCharUseCase
import com.example.domain.usecase.contract.GetNthCharUseCase
import com.example.domain.usecase.contract.GetWordCountUseCase
import com.example.domain.usecase.impl.GetEveryNthCharUseCaseImpl
import com.example.domain.usecase.impl.GetNthCharUseCaseImpl
import com.example.domain.usecase.impl.GetWordCountUseCaseImpl
import com.example.domain2.usecase.contract.FetchContentUsecase
import com.example.domain2.usecase.impl.FetchContentUsecaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provide15CharUseCase(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetNthCharUseCase =
        GetNthCharUseCaseImpl( coroutineDispatcher)

    @Provides
    fun provideAll15CharUseCase(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetEveryNthCharUseCase =
        GetEveryNthCharUseCaseImpl(
            coroutineDispatcher
        )

    @Provides
    fun provideWordCountUseCase(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetWordCountUseCase = GetWordCountUseCaseImpl(
        coroutineDispatcher
    )
    @Provides
    fun provideFetchDataUseCase(
        websiteRepository: WebsiteRepository,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): FetchContentUsecase = FetchContentUsecaseImpl(
        websiteRepository,
        coroutineDispatcher
    )


}