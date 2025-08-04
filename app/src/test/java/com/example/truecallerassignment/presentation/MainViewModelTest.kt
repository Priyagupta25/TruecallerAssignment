package com.example.truecallerassignment.presentation

import com.example.domain.usecase.contract.GetEveryNthCharUseCase
import com.example.domain.usecase.contract.GetNthCharUseCase
import com.example.domain.usecase.contract.GetWordCountUseCase
import com.example.domain2.UiState
import com.example.domain2.usecase.contract.FetchContentUsecase
import com.example.truecallerassignment.presentation.viewmodel.MainViewModel
import com.google.common.truth.Truth.assertThat
import com.sample.template.MainCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var getNthCharUseCase: GetNthCharUseCase

    @Mock
    lateinit var getEveryNthCharUseCase: GetEveryNthCharUseCase

    @Mock
    lateinit var getWordCounterUseCase: GetWordCountUseCase

    @Mock
    lateinit var fetchContentUsecase: FetchContentUsecase
    @get:Rule
    val dispatcherRule = MainCoroutinesRule()


    private val nthValue = 10

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(
            getNthCharUseCase,
            getEveryNthCharUseCase,
            getWordCounterUseCase,
            fetchContentUsecase
        )
    }

    @Test
    fun `test nth char success`() = runTest {
        // Given
        val result = "K"
        val flow = flow {
            emit(UiState.Success(result))
        }

        // When
        `when`(getNthCharUseCase.invoke(nthValue,result)).thenReturn(flow)
        mainViewModel.getNthChar(nthValue,result)

        // Then
        assertThat(mainViewModel.uiState.value.nthChar).isEqualTo(result)
        assertThat(mainViewModel.uiState.value.allNthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.wordCounts).isEmpty()
        assertThat(mainViewModel.uiState.value.errorMessage).isNull()
    }

    @Test
    fun `test nth char failure`() = runTest {
        // Given
        val sampleErrorResponse = "Something Went Wrong!"
        val flow = flow {
            emit(UiState.Failure(sampleErrorResponse))
        }

        // When
        `when`(getNthCharUseCase.invoke(nthValue,sampleErrorResponse)).thenReturn(flow)
        mainViewModel.getNthChar(nthValue,sampleErrorResponse)

        // Then
        assertThat(mainViewModel.uiState.value.wordCounts).isEmpty()
        assertThat(mainViewModel.uiState.value.nthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.allNthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.errorMessage).isNotNull()
        assertThat(mainViewModel.uiState.value.errorMessage).isEqualTo(sampleErrorResponse)
    }

    @Test
    fun `test every nth char success`() = runTest {
        // Given
        val result = "test_string_with_every_nth_character"
        val flow = flow {
            emit(UiState.Success(result))
        }

        // When
        `when`(getEveryNthCharUseCase.invoke(nthValue,result)).thenReturn(flow)
        mainViewModel.getAllNthChar(nthValue,result)

        // Then
        assertThat(mainViewModel.uiState.value.allNthChar).isEqualTo(result)
        assertThat(mainViewModel.uiState.value.nthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.wordCounts).isEmpty()
        assertThat(mainViewModel.uiState.value.errorMessage).isNull()
    }


    @Test
    fun `test every nth char failure`() = runTest {
        // Given
        val sampleErrorResponse = "Something Went Wrong!"
        val flow = flow {
            emit(UiState.Failure(sampleErrorResponse))
        }

        // When
        `when`(getEveryNthCharUseCase.invoke(nthValue,sampleErrorResponse)).thenReturn(flow)
        mainViewModel.getAllNthChar(nthValue,sampleErrorResponse)

        // Then
        assertThat(mainViewModel.uiState.value.wordCounts).isEmpty()
        assertThat(mainViewModel.uiState.value.nthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.allNthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.errorMessage).isNotNull()
        assertThat(mainViewModel.uiState.value.errorMessage).isEqualTo(sampleErrorResponse)
    }


    @Test
    fun `test word counter success`() = runTest {
        // Given
        val result = "word_count"
        val flow = flow {
            emit(UiState.Success(result))
        }

        // When
        `when`(getWordCounterUseCase.invoke(result)).thenReturn(flow)
        mainViewModel.getWordCounter(result)

        // Then
        assertThat(mainViewModel.uiState.value.wordCounts).isEqualTo(result)
        assertThat(mainViewModel.uiState.value.nthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.allNthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.errorMessage).isNull()
    }

    @Test
    fun `test word counter failure`() = runTest {
        // Given
        val sampleErrorResponse = "Something Went Wrong!"
        val flow = flow {
            emit(UiState.Failure(sampleErrorResponse))
        }

        // When
        `when`(getWordCounterUseCase.invoke(sampleErrorResponse)).thenReturn(flow)
        mainViewModel.getWordCounter(sampleErrorResponse)

        // Then
        assertThat(mainViewModel.uiState.value.wordCounts).isEmpty()
        assertThat(mainViewModel.uiState.value.nthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.allNthChar).isEmpty()
        assertThat(mainViewModel.uiState.value.errorMessage).isNotNull()
        assertThat(mainViewModel.uiState.value.errorMessage).isEqualTo(sampleErrorResponse)
    }


}