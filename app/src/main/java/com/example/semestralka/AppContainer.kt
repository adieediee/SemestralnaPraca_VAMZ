package com.example.sem_nova.Data


import android.content.Context
import com.example.semestralka.database.OfflineRecipeRepository
import com.example.semestralka.database.RecipeDatabase
import com.example.semestralka.database.RecipeRepository

/**
 * App container for Dependency injection.
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
interface AppContainer {
    val recipeRepository: RecipeRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val recipeRepository: RecipeRepository by lazy {
        OfflineRecipeRepository(RecipeDatabase.getDatabase(context).recipeDao())    }
}