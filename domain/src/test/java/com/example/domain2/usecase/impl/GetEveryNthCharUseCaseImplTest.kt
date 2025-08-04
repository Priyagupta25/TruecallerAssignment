package com.example.domain2.usecase.impl

import com.example.domain.usecase.impl.GetEveryNthCharUseCaseImpl
import com.example.domain2.UiState
import com.sample.template.MainCoroutinesRule

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList

class GetEveryNthCharUseCaseImplTest {
    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    lateinit var getEveryNthCharUseCase: GetEveryNthCharUseCaseImpl
    private val nthValue = 10

    @Before
    fun setUp() {
        getEveryNthCharUseCase = GetEveryNthCharUseCaseImpl(coroutineRule.testDispatcher)
    }

    @Test
    fun `test fetch every nth char with empty string returns empty`() = runTest {
        val response = ""
        val result = getEveryNthCharUseCase.invoke(nthValue, response)
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEmpty()
    }

    @Test
    fun `test nthValue greater than string length returns empty`() = runTest {
        val response = "short"
        val result = getEveryNthCharUseCase.invoke(100, response)
        val allResult = result.toList()

        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEmpty()
    }

    @Test
    fun `test fetch every nth char success`() = runTest {
        val response = "sample server response"
        val expectedResult =
            "${response[nthValue - 1]},${response[nthValue.times(2).minus(1)]}"
        val result = getEveryNthCharUseCase.invoke(nthValue, response)
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEqualTo(expectedResult)

    }

}