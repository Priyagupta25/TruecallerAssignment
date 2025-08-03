package com.example.tcdata.repository

import com.example.domain.repository.WebsiteRepository
import com.example.network2.api.TruecallerAPI
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class WebsiteRepositoryImplTest {

    private lateinit var websiteRepository: WebsiteRepository

    @Mock
    lateinit var truecallerAPI: TruecallerAPI

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        websiteRepository = WebsiteRepositoryImpl(truecallerAPI)
    }

    @Test
    fun `test fetch content success`() = runTest {
        // sample response
        val response = "test content"

        // run usecase
        `when`(truecallerAPI.fetchWebContent()).thenReturn(Response.success(response))
        val result = websiteRepository.fetchWebsiteText()

        // verify testcase
        assertThat(result).isEqualTo(response)
        Mockito.verify(truecallerAPI, Mockito.times(1)).fetchWebContent()
    }

}