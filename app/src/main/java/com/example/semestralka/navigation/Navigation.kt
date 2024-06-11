package com.example.semestralka.navigation


import SharedViewModelMealCard

import ViewModelFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.semestralka.SharedViewModel
import com.example.semestralka.gui.AddRecipeDestination
import com.example.semestralka.gui.AddRecipeScreen
import com.example.semestralka.gui.MainDestination
import com.example.semestralka.gui.MainScreen
import com.example.semestralka.gui.RecipeDestination
import com.example.semestralka.gui.RecipeInfoDestination
import com.example.semestralka.gui.RecipeInfoScreen
import com.example.semestralka.gui.RecipeScreen

import com.example.semestralka.gui.notesscreen.NotesDestination
import com.example.semestralka.gui.notesscreen.NotesScreen
import com.example.semestralka.gui.notesscreen.ShoppingListViewModel


@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val sharedViewModel: SharedViewModel = viewModel(factory = ViewModelFactory)
    val shoppingListViewModel: ShoppingListViewModel = viewModel(factory = ViewModelFactory)
    val sharedViewModelMealCard: SharedViewModelMealCard = viewModel(factory = ViewModelFactory)

    NavHost(navController = navController,
        startDestination = MainDestination.route,
        modifier = modifier
    ) {
        composable(route = MainDestination.route) {
            MainScreen(
                onPrevious = { navController.navigate(NotesDestination.route) },
                onNext = { navController.navigate(RecipeDestination.route) },
                viewModel = shoppingListViewModel,
                sharedViewModelMealCard = sharedViewModelMealCard
            )
        }
        composable(route = RecipeDestination.route) {
            RecipeScreen(
                onRecipeClick = { navController.navigate(RecipeInfoDestination.route) },
                onPrevious = { navController.navigate(MainDestination.route) },
                onAdd = { navController.navigate(AddRecipeDestination.route) },
                sharedViewModel = sharedViewModel,
            )
        }
        composable(route = RecipeInfoDestination.route) { backStackEntry ->
            RecipeInfoScreen(
                onBack = { navController.navigateUp() },
                onEdit = { recipeId -> navController.navigate("${AddRecipeDestination.route}/$recipeId") },
                onDelete = { recipeId ->
                    sharedViewModel.deleteRecipe(recipeId)
                    navController.navigateUp() // Navigate back after deletion
                },
                sharedViewModel = sharedViewModel,
                sharedViewModelMealCard = sharedViewModelMealCard,
                shoppingListViewModel = shoppingListViewModel
            )
        }
        composable(
            route = "${AddRecipeDestination.route}/{${AddRecipeDestination.recipeIdArg}}",
            arguments = listOf(navArgument(AddRecipeDestination.recipeIdArg) { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(AddRecipeDestination.recipeIdArg)
            AddRecipeScreen(navController = navController, recipeId = recipeId)
        }
        composable(route = NotesDestination.route) {
            NotesScreen(
                onNext = { navController.navigate(MainDestination.route) },
                viewModel = shoppingListViewModel
            )
        }
        composable(route = AddRecipeDestination.route) {
            AddRecipeScreen(navController = navController)
        }
    }
}
