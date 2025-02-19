package com.example.getupdated.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.launchUrl(url: String) {
    val builder = CustomTabsIntent.Builder()
        .setInitialActivityHeightPx(200)
        .setCloseButtonPosition(CustomTabsIntent.CLOSE_BUTTON_POSITION_END)
        .build()

    builder.launchUrl(this, Uri.parse(url))
}