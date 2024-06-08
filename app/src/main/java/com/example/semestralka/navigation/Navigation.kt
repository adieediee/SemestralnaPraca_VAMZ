package com.example.semestralka.navigation

import MainDestination
import MainScreen
import RecipeDestination
import RecipeInfoDestination
import RecipeInfoScreen
import RecipeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semestralka.gui.mainscreen.NotesDestination

import com.example.semestralka.gui.mainscreen.NotesScreen


@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(navController = navController,
        startDestination = MainDestination.route,
        modifier = modifier
        ) {
        composable(route = MainDestination.route) {
            MainScreen(
                onPrevious = {navController.navigate(NotesDestination.route)},
                onNext = {navController.navigate(RecipeDestination.route)}
            )
        }
        composable(route = RecipeDestination.route) {
            RecipeScreen({navController.navigate(RecipeInfoDestination.route)})
        }
        composable(route = RecipeInfoDestination.route) {
            RecipeInfoScreen(onBack = {navController.navigate(MainDestination.route)}) {
            }
        }
        composable(route = NotesDestination.route) {
            NotesScreen(onNext = {navController.navigate(MainDestination.route)})

        }
    }
}