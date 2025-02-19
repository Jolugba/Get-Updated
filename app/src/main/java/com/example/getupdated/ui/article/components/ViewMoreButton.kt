package com.example.getupdated.ui.article.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.getupdated.util.launchUrl


@Composable
fun ViewMoreButton(url: String) {
    val context = LocalContext.current
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { context.launchUrl(url)
    }) {
        Text(text ="Read Full Article")
    }
}
