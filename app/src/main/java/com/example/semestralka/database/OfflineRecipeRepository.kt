package com.example.semestralka.database

import kotlinx.coroutines.flow.Flow

class OfflineRecipeRepository(private val recipeDao: RecipeDao) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()
    override fun getRecipeStream(id: Int): Flow<Recipe?> = recipeDao.getRecipe(id)
    override suspend fun insertRecipe(recipe: Recipe) = recipeDao.insert(recipe)
    override suspend fun deleteRecipe(recipe: Recipe) = recipeDao.delete(recipe)
    override suspend fun updateRecipe(recipe: Recipe) = recipeDao.update(recipe)
}
