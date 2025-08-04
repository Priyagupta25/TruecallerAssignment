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
        val response = "test content"

        `when`(truecallerAPI.fetchWebContent()).thenReturn(Response.success(response))
        val result = websiteRepository.fetchWebsiteText()

        assertThat(result).isEqualTo(response)
        Mockito.verify(truecallerAPI, Mockito.times(1)).fetchWebContent()
    }
    @Test(expected = Exception::class)
    fun `test fetch content failure (HTTP error)`() = runTest {

        `when`(truecallerAPI.fetchWebContent()).thenReturn(Response.error(404, okhttp3.ResponseBody.create(null, "Not Found")))

        websiteRepository.fetchWebsiteText() // Should throw Exception in your RepoImpl
    }

    @Test(expected = RuntimeException::class)
    fun `test fetch content throws network exception`() = runTest {

        `when`(truecallerAPI.fetchWebContent()).thenThrow(RuntimeException("Network error"))

        websiteRepository.fetchWebsiteText() // Should throw RuntimeException
    }

}