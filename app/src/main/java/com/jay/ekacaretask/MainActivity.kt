package com.jay.ekacaretask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jay.ekacaretask.ui.theme.EkaCareTaskTheme
import com.jay.ekacaretask.view.screen.LandingScreen
import com.jay.ekacaretask.view.screen.SavedScreen
import com.jay.ekacaretask.view.screen.Web
import com.jay.ekacaretask.view.componets.ArticleWebViewScreen
import com.jay.ekacaretask.view.componets.BottomNavItem
import com.jay.ekacaretask.view.componets.BottomNavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            EkaCareTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) }) { innerPadding ->

                    NavHost(
                        navController,
                        startDestination = BottomNavItem.Home.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavItem.Home.route) { LandingScreen(navController) }
                        composable(BottomNavItem.Saved.route) { SavedScreen(navController) }
                        composable<Web> {
                            val args = it.toRoute<Web>()
                            ArticleWebViewScreen(
                                url = args.url,
                                title = args.title,
                                description = args.description,
                                urlToImage = args.urlToImage
                            )
                        }
                    }


                }
            }
        }
    }
}
