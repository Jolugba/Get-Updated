package com.example.getupdated.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.getupdated.data.network.Articles
import com.example.getupdated.ui.article.ArticleDetailScreen
import com.example.getupdated.ui.article.ArticleListScreen
import com.google.gson.Gson

@Composable
fun NYTimesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "article_list",
        modifier = modifier
    ) {
        composable("article_list") {
            ArticleListScreen(
                onArticleClick = { article ->
                    // Convert article to JSON and URL encode it
                    val articleJson = Gson().toJson(article)
                    val encodedJson = Uri.encode(articleJson)
                    navController.navigate("article_detail/$encodedJson")
                }
            )
        }

        composable("article_detail/{article}") { backStackEntry ->
            // Retrieve the encoded JSON string from the URI parameter
            val encodedArticleJson = backStackEntry.arguments?.getString("article")
            encodedArticleJson?.let {
                // URL decode and convert back to article object
                val decodedJson = Uri.decode(it)
                val article = Gson().fromJson(decodedJson, Articles::class.java)
                ArticleDetailScreen(
                    article = article,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}