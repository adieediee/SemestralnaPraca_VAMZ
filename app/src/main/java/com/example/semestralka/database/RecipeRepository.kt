package com.example.semestralka.database

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(id: Int): Recipe?
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
}