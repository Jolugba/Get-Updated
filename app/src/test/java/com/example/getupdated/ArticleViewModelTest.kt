package com.example.getupdated

import com.example.getupdated.data.network.Articles
import com.example.getupdated.data.repo.ArticleRepository
import com.example.getupdated.ui.article.ArticleViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleViewModelTest {

    private lateinit var viewModel: ArticleViewModel
    private val repository: ArticleRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loadArticles is called, then UiState should be updated accordingly`() = runTest {
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
        coEvery { repository.getMostViewedArticles(any()) } returns articles
        
        // When
        viewModel = ArticleViewModel(repository)
        
        // Collect state changes
        val states = mutableListOf<ArticleViewModel.UiState>()
        val job = launch {
            viewModel.uiState.collect { states.add(it) }
        }
        advanceUntilIdle()
        
        // Then
        assertEquals(2, states.size)
        assertTrue(states[0] is ArticleViewModel.UiState.Loading)
        assertTrue(states[1] is ArticleViewModel.UiState.Success)
        
        val successState = states[1] as ArticleViewModel.UiState.Success
        assertEquals(articles, successState.articles)
        
        job.cancel()
    }

    @Test
    fun `when repository throws exception, then UiState should be Error`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { repository.getMostViewedArticles(any()) } throws RuntimeException(errorMessage)
        
        // When
        viewModel = ArticleViewModel(repository)
        
        // Collect state changes
        val states = mutableListOf<ArticleViewModel.UiState>()
        val job = launch {
            viewModel.uiState.collect { states.add(it) }
        }
        advanceUntilIdle()
        
        // Then
        assertEquals(2, states.size)
        assertTrue(states[0] is ArticleViewModel.UiState.Loading)
        assertTrue(states[1] is ArticleViewModel.UiState.Error)
        
        val errorState = states[1] as ArticleViewModel.UiState.Error
        assertEquals(errorMessage, errorState.message)
        
        job.cancel()
    }
}