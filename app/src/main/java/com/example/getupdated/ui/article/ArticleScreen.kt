package com.example.getupdated.ui.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.getupdated.data.network.Articles
import com.example.getupdated.ui.article.components.ArticleList
import com.example.getupdated.ui.article.components.EmptyStateMessage
import com.example.getupdated.ui.article.components.ErrorStateMessage
import com.example.getupdated.ui.article.components.PeriodSelectionRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    onArticleClick: (Articles) -> Unit,
    viewModel: ArticleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedPeriod by remember { mutableStateOf(1) }

    // This effect runs once upon first composition
    LaunchedEffect(Unit) {
        viewModel.loadArticles(selectedPeriod)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Most Popular Articles") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            PeriodSelectionRow(
                selectedPeriod = selectedPeriod,
                onPeriodSelected = { period ->
                    selectedPeriod = period
                    viewModel.loadArticles(period)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                when (val state = uiState) {
                    is ArticleViewModel.UiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is ArticleViewModel.UiState.Success -> {
                        val articles = state.articles
                        if (articles.isNullOrEmpty()) {
                            EmptyStateMessage(
                                message = "No articles found.",
                                onRetryClick = { viewModel.loadArticles(selectedPeriod) }
                            )
                        } else {
                            ArticleList(
                                articles = articles,
                                onArticleClick = onArticleClick
                            )
                        }
                    }

                    is ArticleViewModel.UiState.Error -> {
                        ErrorStateMessage(
                            errorMessage = state.message,
                            onRetryClick = { viewModel.loadArticles(selectedPeriod) }
                        )
                    }
                }
            }
        }
    }
}
