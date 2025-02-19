package com.example.getupdated

import com.example.getupdated.data.network.Articles
import com.example.getupdated.data.network.GetUpdatedApiService
import com.example.getupdated.data.network.GetUpdatedResponse
import com.example.getupdated.data.repo.ArticleRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ArticleRepositoryImplTest {

    private lateinit var repository: ArticleRepositoryImpl
    private val apiService: GetUpdatedApiService = mockk()

    @Before
    fun setup() {
        repository = ArticleRepositoryImpl(apiService)
    }

    @Test
    fun `getMostViewedArticles should return list of articles from API`() = runBlocking {
        // Given
        val articles = listOf(
            Articles(
                id = 1,
                url = "https://example.com",
                title = "Test Title",
                abstract = "Test Abstract",
                byline = "By Test Author",
                published_date = "2023-01-01",
                section = "Test Section",
                subsection = "Test Subsection",
                media = null,
            )
        )
        val response = GetUpdatedResponse(
            status = "OK",
            copyright = "Copyright",
            num_results = articles.size,
            results = articles
        )}}
