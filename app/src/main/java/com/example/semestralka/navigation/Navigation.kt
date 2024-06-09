package com.example.semestralka.navigation




import MainDestination
import MainScreen

import RecipeInfoDestination
import RecipeInfoScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semestralka.gui.AddRecipeDestination
import com.example.semestralka.gui.AddRecipeScreen
import com.example.semestralka.gui.RecipeDestination
import com.example.semestralka.gui.RecipeScreen

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
            RecipeScreen(onRecipeClick = {navController.navigate(RecipeInfoDestination.route)},
               onPrevious = {navController.navigate(MainDestination.route)},
                onAdd = {navController.navigate(AddRecipeDestination.route)}
            )
        }
        composable(route = RecipeInfoDestination.route) {
            RecipeInfoScreen(onBack = {navController.navigate(MainDestination.route)}) {
            }
        }
        composable(route = NotesDestination.route) {
            NotesScreen(onNext = {navController.navigate(MainDestination.route)})
        }
        composable(route = AddRecipeDestination.route) {
            AddRecipeScreen(navController)
        }
    }
}