package com.example.domain2.usecase.impl

import com.example.domain.repository.WebsiteRepository
import com.example.domain.usecase.contract.GetWordCountUseCase
import com.example.domain.usecase.impl.GetWordCountUseCaseImpl
import com.example.domain2.UiState
import com.example.domain2.usecase.contract.FetchContentUsecase
import com.google.common.truth.Truth.assertThat
import com.sample.template.MainCoroutinesRule
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class FetchContentUsecaseImplTest {

    @get:Rule
    var coroutineRule = MainCoroutinesRule()
    private lateinit var fetchContentUsecase: FetchContentUsecase
    @Mock
    lateinit var websiteRepository: WebsiteRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchContentUsecase =
            FetchContentUsecaseImpl(
                websiteRepository,
                coroutineRule.testDispatcher
            )
    }

    @Test
    fun `test fetch content success`() = runTest {
        // sample
        val response = "sample test data"
      //request
        `when`(websiteRepository.fetchWebsiteText()).thenReturn(response)
        val result = fetchContentUsecase.invoke()

     //verificationo f usecase
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat(allResult.last()).isInstanceOf(UiState.Success::class.java)
        assertThat((allResult.last() as UiState.Success).data.trim()).isEqualTo(response)
        verify(websiteRepository, times(1)).fetchWebsiteText()
    }




}