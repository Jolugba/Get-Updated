package com.example.getupdated.data.repo

import com.example.getupdated.data.network.Articles

interface ArticleRepository {
    suspend fun getMostViewedArticles(period: Int): List<Articles>?
}