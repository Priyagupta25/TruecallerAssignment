package com.example.domain2.usecase.impl


import com.example.domain.usecase.impl.GetNthCharUseCaseImpl
import com.example.domain2.UiState
import com.google.common.truth.Truth.assertThat
import com.sample.template.MainCoroutinesRule
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetNthCharUseCaseImplTest {

    @get:Rule
    var coroutineRule = MainCoroutinesRule()
    lateinit var getNthCharUseCase: GetNthCharUseCaseImpl
    private val nthValue = 10

    @Before
    fun setUp() {
        getNthCharUseCase = GetNthCharUseCaseImpl(coroutineRule.testDispatcher)
    }

    @Test
    fun `test fetch every nth char success`() = runTest {
        val response = "sample server response"
        val result = getNthCharUseCase.invoke(nthValue, response)
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEqualTo(response[nthValue.minus(1)].toString())

    }

    @Test
    fun `test nthValue greater than string length returns empty`() = runTest {
        val response = "short"
        val result = getNthCharUseCase.invoke(100, response)
        val allResult = result.toList()

        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEmpty()
    }

    @Test
    fun `test nthValue is zero returns empty`() = runTest {
        val response = "sample text"
        val result = getNthCharUseCase.invoke(0, response)
        val allResult = result.toList()

        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEmpty()
    }


}
