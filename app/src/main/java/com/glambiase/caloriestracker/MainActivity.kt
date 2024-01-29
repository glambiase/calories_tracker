package com.glambiase.caloriestracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.glambiase.caloriestracker.navigation.navigate
import com.glambiase.caloriestracker.ui.theme.CaloriesTrackerTheme
import com.glambiase.core.navigation.Route
import com.glambiase.onboarding_presentation.activity_level.ActivityLevelScreen
import com.glambiase.onboarding_presentation.age.AgeScreen
import com.glambiase.onboarding_presentation.goal.GoalScreen
import com.glambiase.onboarding_presentation.height.HeightScreen
import com.glambiase.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.glambiase.onboarding_presentation.sex.SexScreen
import com.glambiase.onboarding_presentation.weight.WeightScreen
import com.glambiase.onboarding_presentation.welcome.WelcomeScreen
import com.glambiase.tracker_presentation.search.SearchScreen
import com.glambiase.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.ACTIVITY_LEVEL) {
                            ActivityLevelScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.GOAL) {
                            GoalScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    navArgument("dayOfMonth") {
                                        type = NavType.IntType
                                    }
                                },
                                navArgument("month") {
                                    navArgument("month") {
                                        type = NavType.IntType
                                    }
                                },
                                navArgument("year") {
                                    navArgument("year") {
                                        type = NavType.IntType
                                    }
                                }
                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName").orEmpty()
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth") ?: 1
                            val month = it.arguments?.getInt("month") ?: 1
                            val year = it.arguments?.getInt("year") ?: 1970
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}