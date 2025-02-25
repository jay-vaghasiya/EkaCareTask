package com.jay.ekacaretask.view.componets

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jay.ekacaretask.model.datamodel.local.Articles
import com.jay.ekacaretask.viewmodel.LocalNewsViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleWebViewScreen(
    url: String,
    title: String,
    description: String,
    urlToImage: String
) {
    val viewModel: LocalNewsViewModel = koinViewModel()
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.apply {
                        javaScriptEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        setSupportZoom(true)
                    }
                    webViewClient = WebViewClient()
                }
            },
            update = { webView -> webView.loadUrl(url) },
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {
                viewModel.saveArticle(
                    Articles(
                        url = url,
                        title = title,
                        urlOfImage = urlToImage,
                        description = description
                    )
                )
                Toast.makeText(context,"Article Saved",Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Save Article")
        }
    }
}
