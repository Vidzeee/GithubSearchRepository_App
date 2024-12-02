package com.example.gitdev_assign.api

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun WebViewScreen(url: String) {
    // Get the context using LocalContext
    val context = LocalContext.current

    // Initialize the WebView
    val webView = remember { WebView(context) }

    val decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())

    // Load the URL into the WebView
    LaunchedEffect(decodedUrl) {
        if (decodedUrl.isNotEmpty()) {
            webView.loadUrl(decodedUrl)
        }
    }
}


