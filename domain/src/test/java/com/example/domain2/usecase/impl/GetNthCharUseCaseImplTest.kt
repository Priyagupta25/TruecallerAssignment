package com.example.domain2.usecase.impl

import com.example.domain.usecase.contract.GetNthCharUseCase
import com.example.domain.usecase.impl.GetEveryNthCharUseCaseImpl
import com.example.domain.usecase.impl.GetNthCharUseCaseImpl
import com.example.domain2.IoDispatcher
import com.example.domain2.UiState
import com.google.common.truth.Truth.assertThat
import com.sample.template.MainCoroutinesRule
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class GetNthCharUseCaseImplTest {

    @get:Rule
    var coroutineRule = MainCoroutinesRule()
    lateinit var SUT: GetNthCharUseCaseImpl
    private val nthValue = 10

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        SUT = GetNthCharUseCaseImpl( coroutineRule.testDispatcher)
    }

    @Test
    fun `test fetch every nth char success`() = runTest {
        val response = "sample server response"
        val result = SUT.invoke(nthValue,response)
        val allResult = result.toList()
        assertThat(allResult.first()).isInstanceOf(UiState.Loading::class.java)
        assertThat((allResult.last() as UiState.Success).data).isEqualTo(response[nthValue.minus(1)].toString())

    }



}
