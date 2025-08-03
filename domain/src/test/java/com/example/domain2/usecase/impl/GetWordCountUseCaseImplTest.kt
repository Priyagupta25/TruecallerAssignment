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
import org.mockito.MockitoAnnotations

class GetWordCountUseCaseImplTest {
    @get:Rule
    var coroutineRule = MainCoroutinesRule()
    private lateinit var getWordCounterUseCase: GetWordCountUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getWordCounterUseCase =
            GetWordCountUseCaseImpl(
                coroutineRule.testDispatcher
            )
    }
    @Test
    fun `test fetch word count success`() = runTest {
        //Sample text an response
        val response = "sample sample sample"
        val expectedResult = "sample: 3"
        val result = getWordCounterUseCase.invoke(response)
        //verifying usecase
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat(allResult.last()).isInstanceOf(UiState.Success::class.java)
        assertThat((allResult.last() as UiState.Success).data.trim()).isEqualTo(expectedResult.trim())

    }


}