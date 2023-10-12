package com.glambiase.caloriestracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.glambiase.caloriestracker.navigation.navigate
import com.glambiase.caloriestracker.ui.theme.CaloriesTrackerTheme
import com.glambiase.core.navigation.Route
import com.glambiase.onboarding_presentation.age.AgeScreen
import com.glambiase.onboarding_presentation.sex.SexScreen
import com.glambiase.onboarding_presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriesTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME,
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.SEX) {
                            SexScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.HEIGHT) {
                        }
                        composable(Route.WEIGHT) {
                        }
                        composable(Route.NUTRIENT_GOAL) {
                        }
                        composable(Route.ACTIVITY) {
                        }
                        composable(Route.GOAL) {
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                        }
                        composable(Route.SEARCH) {
                        }
                    }
                }
            }
        }
    }
}