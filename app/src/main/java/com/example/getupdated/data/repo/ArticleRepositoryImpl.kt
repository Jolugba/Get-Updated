package com.example.getupdated.data.repo

import com.example.getupdated.data.network.Articles
import com.example.getupdated.data.network.GetUpdatedApiService
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val nyTimesApiService: GetUpdatedApiService
) : ArticleRepository {

    override suspend fun getMostViewedArticles(period: Int): List<Articles>? {
        val response = nyTimesApiService.getRecentArticles(period)
        if (response.isSuccessful) {
            return response.body()?.results
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"

            when (response.code()) {
                408 -> throw TimeoutException(parseErrorMessage(errorBody))
                503 -> throw NoInternetException(parseErrorMessage(errorBody))
                else -> throw ServerException(parseErrorMessage(errorBody))
            }
        }
    }

    /**
     * Optionally, parse the JSON error message returned by the interceptor, e.g.:
     * { "error": "No Internet Connection" }
     */
    private fun parseErrorMessage(errorBody: String): String {
        return try {
            val json = JSONObject(errorBody)
            json.optString("error", "Unknown error")
        } catch (e: Exception) {
            // Fallback if parsing fails
            errorBody
        }
    }
}

/** Thrown when the request times out (HTTP 408). */
class TimeoutException(message: String) : IOException(message)

/** Thrown when there is no internet connection or a 503 from the interceptor. */
class NoInternetException(message: String) : IOException(message)

/** Thrown for general server-side or unknown I/O errors (e.g., status code 500). */
class ServerException(message: String) : Exception(message)
