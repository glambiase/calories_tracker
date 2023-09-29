package com.glambiase.caloriestracker.navigation

import androidx.navigation.NavController
import com.glambiase.core.util.UIEvent

fun NavController.navigate(event: UIEvent.Navigate) = navigate(event.route)