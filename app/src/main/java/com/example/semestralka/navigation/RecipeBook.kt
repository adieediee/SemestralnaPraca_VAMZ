package com.example.semestralka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.semestralka.navigation.Navigation
@Composable
fun RecipeBookApp(navController: NavHostController = rememberNavController()) {
    Navigation(navController = navController)
}