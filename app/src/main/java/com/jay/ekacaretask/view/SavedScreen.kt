package com.jay.ekacaretask.view

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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.SubcomposeAsyncImage
import com.jay.ekacaretask.model.datamodel.local.Articles
import com.jay.ekacaretask.viewmodel.LocalNewsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedScreen(navController: NavHostController) {
    val viewModel: LocalNewsViewModel = koinViewModel()
    val savedarticleState by viewModel.savedArticles.collectAsState()

    AnimatedContent(
        targetState = savedarticleState,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        }, label = "contentAnimation"
    ) { targetState ->
        if (targetState.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(12.dp)) {
                items(savedarticleState) { article ->
                    ArticlesItem(article) {
                        navController.navigate(
                            Web(
                                url = article.url,
                                title = article.title,
                                description = article.description,
                                urlToImage = article.urlOfImage,
                            )
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No Saved Articles", modifier = Modifier.basicMarquee())
            }
        }
    }
}

@Composable
fun ArticlesItem(article: Articles, onClick: () -> Unit) {
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
                model = article.urlOfImage,
                modifier = Modifier
                    .size(136.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentDescription = article.title,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = article.description,
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
