package com.example.semestralka.navigation




import MainDestination
import MainScreen
import ShoppingListViewModel
import android.util.Log


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semestralka.SharedViewModel
import com.example.semestralka.database.Recipe
import com.example.semestralka.gui.AddRecipeDestination
import com.example.semestralka.gui.AddRecipeScreen
import com.example.semestralka.gui.RecipeDestination
import com.example.semestralka.gui.RecipeInfoDestination
import com.example.semestralka.gui.RecipeInfoScreen
import com.example.semestralka.gui.RecipeScreen

import com.example.semestralka.gui.mainscreen.NotesDestination

import com.example.semestralka.gui.mainscreen.NotesScreen


@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier){
    val sharedViewModel: SharedViewModel = viewModel()
    val shoppingListViewModel: ShoppingListViewModel = viewModel()
    NavHost(navController = navController,
        startDestination = MainDestination.route,
        modifier = modifier
        ) {
        composable(route = MainDestination.route) {
            MainScreen(
                onPrevious = {navController.navigate(NotesDestination.route)},
                onNext = {navController.navigate(RecipeDestination.route)},
                viewModel = shoppingListViewModel
            )
        }
        composable(route = RecipeDestination.route) {
            RecipeScreen(
                onRecipeClick = {navController.navigate(RecipeInfoDestination.route)},
               onPrevious = {navController.navigate(MainDestination.route)},
                onAdd = {navController.navigate(AddRecipeDestination.route)},
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = RecipeInfoDestination.route) { backStackEntry ->

                RecipeInfoScreen(
                    onBack = { navController.navigateUp() },
                    onEdit = { /* Implement edit action */ } ,
                            sharedViewModel = sharedViewModel
                )



        }
        composable(route = NotesDestination.route) {
            NotesScreen(
                onNext = {navController.navigate(MainDestination.route)},
                viewModel = shoppingListViewModel
            )
        }
        composable(route = AddRecipeDestination.route) {
            AddRecipeScreen(navController)
        }
    }
}