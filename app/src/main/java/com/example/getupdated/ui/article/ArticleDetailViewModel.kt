package com.example.getupdated.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getupdated.data.network.Articles
import com.example.getupdated.data.repo.ArticleRepository
import com.example.getupdated.data.repo.NoInternetException
import com.example.getupdated.data.repo.ServerException
import com.example.getupdated.data.repo.TimeoutException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    sealed class UiState {
        object Loading : UiState()
        data class Success(val articles: List<Articles>?) : UiState()
        data class Error(val message: String) : UiState()
    }

    /**
     * Loads articles for the given period (e.g., 7, 10, or 30 days).
     */
    fun loadArticles(period: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val articles = articleRepository.getMostViewedArticles(period)
                _uiState.value = UiState.Success(articles)
            } catch (e: TimeoutException) {
                _uiState.value = UiState.Error("Request timed out: ${e.message}")
            } catch (e: NoInternetException) {
                _uiState.value = UiState.Error("No internet connection. Please try again.")
            } catch (e: ServerException) {
                _uiState.value = UiState.Error("Server error occurred: ${e.message}")
            } catch (e: Exception) {
                // Fallback for anything else not caught above
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}
