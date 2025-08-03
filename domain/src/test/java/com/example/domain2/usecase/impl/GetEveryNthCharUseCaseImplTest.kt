package com.example.domain2.usecase.impl

import com.example.domain.usecase.impl.GetEveryNthCharUseCaseImpl
import com.example.domain2.UiState
import com.sample.template.MainCoroutinesRule

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList

class GetEveryNthCharUseCaseImplTest {
    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    lateinit var SUT: GetEveryNthCharUseCaseImpl
    private val nthValue = 10

    @Before
    fun setUp() {
       MockitoAnnotations.openMocks(this)
        SUT = GetEveryNthCharUseCaseImpl( coroutineRule.testDispatcher)
    }

    @Test
    fun `test fetch every nth char success`() = runTest {
        val response = "sample server response"
        val expectedResult =
            "${response[nthValue - 1]},${response[nthValue.times(2).minus(1)]}"
        val result = SUT.invoke(nthValue,response)
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEqualTo(expectedResult)

    }

}