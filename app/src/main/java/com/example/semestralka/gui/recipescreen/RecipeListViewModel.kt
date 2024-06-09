package com.example.semestralka.gui

import androidx.lifecycle.ViewModel
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {
    val allRecipes: Flow<List<Recipe>> = repository.getAllRecipes()
}
