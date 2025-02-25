package com.jay.ekacaretask.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    var showDialog by remember { mutableStateOf(false) }

    AnimatedContent(
        targetState = savedarticleState,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        }, label = "contentAnimation"
    ) { targetState ->
        if (targetState.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(12.dp)) {
                items(savedarticleState) { article ->
                    ArticlesItem(article, onDeleteClick = {
                        showDialog = true
                    }) {
                        navController.navigate(
                            Web(
                                url = article.url,
                                title = article.title,
                                description = article.description,
                                urlToImage = article.urlOfImage,
                            )
                        )
                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Delete Article") },
                            text = { Text("Are you sure you want to delete this article?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.deleteArticle(
                                        article = Articles(
                                            article.id,
                                            article.title,
                                            article.description,
                                            article.urlOfImage,
                                            article.url
                                        )
                                    )
                                    showDialog = false
                                }) {
                                    Text("Delete")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("Cancel")
                                }
                            }
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
fun ArticlesItem(article: Articles, onDeleteClick: () -> Unit, onClick: () -> Unit) {
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            TextButton(
                onClick = { onClick() },
            ) {
                Text(
                    text = "Read More",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
            IconButton(onClick = { onDeleteClick() }) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)

            }

        }

        }


}
