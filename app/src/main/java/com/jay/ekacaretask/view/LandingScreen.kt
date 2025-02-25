package com.jay.ekacaretask.view

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil3.compose.SubcomposeAsyncImage
import com.jay.ekacaretask.model.datamodel.Article
import com.jay.ekacaretask.model.datamodel.NewsResponse
import com.jay.ekacaretask.model.datamodel.local.Articles
import com.jay.ekacaretask.view.componets.TripleOrbitLoading
import com.jay.ekacaretask.viewmodel.LocalNewsViewModel
import com.jay.ekacaretask.viewmodel.NewsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LandingScreen(navController: NavHostController) {
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isOffline = remember {
        mutableStateOf(
            connectivityManager.activeNetwork == null
        )
    }

    val viewModel: NewsViewModel = koinViewModel()
    val newsResponse by viewModel.newsResponse.collectAsState()
    val errorResponse by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOffline.value = false

                viewModel.fetchNews("us", "1bf37e19d529477290870276dbdc5c3d")
            }

            override fun onLost(network: Network) {
                isOffline.value = true
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }


    AnimatedContent(
        targetState = isOffline.value to newsResponse,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        }, label = "contentAnimation"
    ) { (offline, response) ->
        if (offline) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                response?.let { news ->
                    LandingContent(news, navController)
                }
                Text(
                    text = "You are offline. Please check your connection.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
        } else {

            response?.let { news ->
                LandingContent(news, navController)
            } ?: run {
                if (errorResponse != null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorResponse.toString(),
                            color = Color.Red,
                            fontSize = 18.sp
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TripleOrbitLoading(modifier = Modifier.size(120.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun LandingContent(newsResponse: NewsResponse?, navController: NavHostController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(newsResponse?.articles ?: emptyList()) { article ->
            ArticleItem(article) {
                navController.navigate(
                    Web(
                        url = article.url.toString(),
                        title = article.title.toString(),
                        description = article.description.toString(),
                        urlToImage = article.urlToImage.toString(),
                    )
                )
            }
        }

    }


}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SubcomposeAsyncImage(
                model = article.urlToImage,
                modifier = Modifier
                    .size(136.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        TripleOrbitLoading(modifier = Modifier.align(Alignment.Center))
                    }
                },
                error = {
                    Icon(contentDescription = null, imageVector = Icons.Default.Newspaper)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = article.title.toString(),
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = article.description.toString(),
                    fontSize = 14.sp,
                    maxLines = 3,
                    fontWeight = FontWeight.Normal
                )

            }

        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = "Read More",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable {
                        onClick()
                    })
        }


    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleWebViewScreen(
    url: String,
    title: String,
    description: String,
    urlToImage: String
) {
    val viewModel: LocalNewsViewModel = koinViewModel()
    val savedState by viewModel.savedArticles.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {

        // WebView using AndroidView
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            },
            modifier = Modifier.weight(1f)
        )

        // Save Button
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
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Save Article")
        }
    }
}

