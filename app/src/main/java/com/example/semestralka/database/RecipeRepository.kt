package com.example.semestralka.database

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(id: Int): Recipe?
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun getSelectedRecipe(): Recipe?

    suspend fun selectRecipe(recipe: Recipe)
    fun searchRecipesByName(searchText: String): Flow<List<Recipe>>
}