package com.example.semestralka

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka.database.RecipeRepository
import com.example.semestralka.gui.AddRecipeViewModel
import com.example.semestralka.gui.RecipeListViewModel

object ViewModelFactory : ViewModelProvider.Factory {

    lateinit var recipeRepository: RecipeRepository

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddRecipeViewModel::class.java) -> {
                AddRecipeViewModel(recipeRepository) as T
            }
            modelClass.isAssignableFrom(RecipeListViewModel::class.java) -> {
                RecipeListViewModel(recipeRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun init(repository: RecipeRepository) {
        recipeRepository = repository
    }
}
