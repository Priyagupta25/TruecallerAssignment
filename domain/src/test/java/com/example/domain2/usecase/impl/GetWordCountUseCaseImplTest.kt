package com.example.domain2.usecase.impl

import com.example.domain.usecase.contract.GetWordCountUseCase
import com.example.domain.usecase.impl.GetWordCountUseCaseImpl
import com.example.domain2.UiState
import com.google.common.truth.Truth.assertThat
import com.sample.template.MainCoroutinesRule
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetWordCountUseCaseImplTest {
    @get:Rule
    var coroutineRule = MainCoroutinesRule()
    private lateinit var getWordCountUseCase: GetWordCountUseCase

    @Before
    fun setup() {
        getWordCountUseCase =
            GetWordCountUseCaseImpl(
                coroutineRule.testDispatcher
            )
    }
    @Test
    fun `test fetch word count success`() = runTest {

        val response = "sample sample sample"
        val expectedResult = "sample: 3"
        val result = getWordCountUseCase.invoke(response)

        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat(allResult.last()).isInstanceOf(UiState.Success::class.java)
        assertThat((allResult.last() as UiState.Success).data.trim()).isEqualTo(expectedResult.trim())
    }

    @Test
    fun `test fetch word count with empty string returns empty`() = runTest {
        val response = ""
        val result = getWordCountUseCase.invoke(response)
        val allResult = result.toList()

        assertThat(allResult).hasSize(2)
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEmpty()
    }

    @Test
    fun `test fetch word count with multiple words`() = runTest {
        val response = "sample test sample"
        val expectedResult = "sample: 2\ntest: 1"

        val result = getWordCountUseCase.invoke(response)
        val allResult = result.toList()

        assertThat(allResult).hasSize(2)
        assertThat((allResult.last() as UiState.Success).data.trim()).isEqualTo(expectedResult)
    }

    @Test
    fun `test fetch word count case insensitive`() = runTest {
        val response = "Sample sample SAMPLE"
        val expectedResult = "sample: 3"

        val result = getWordCountUseCase.invoke(response)
        val allResult = result.toList()

        assertThat(allResult).hasSize(2)
        assertThat((allResult.last() as UiState.Success).data.trim()).isEqualTo(expectedResult)
    }

}