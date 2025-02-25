package com.jay.ekacaretask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jay.ekacaretask.ui.theme.EkaCareTaskTheme
import com.jay.ekacaretask.view.componets.TripleOrbitLoading
import com.jay.ekacaretask.viewmodel.NewsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: NewsViewModel = koinViewModel()
            val newsResponse by viewModel.newsResponse.collectAsState()
            val errorResponse by viewModel.errorMessage.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.fetchNews("us", "1bf37e19d529477290870276dbdc5c3d")
            }
            EkaCareTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (newsResponse != null) {
                        Text(
                            text = newsResponse.toString(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                    } else if(errorResponse != null){
                        Text(
                            text = errorResponse.toString(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                    }else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            TripleOrbitLoading( modifier = Modifier.size(120.dp))
                        }
                    }

                }
            }
        }
    }
}

