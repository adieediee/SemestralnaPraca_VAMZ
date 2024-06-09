package com.example.semestralka.database

import android.content.Context

interface AppContainer {
    val recipeRepository: RecipeRepository
}

class AppContainerImpl(context: Context) : AppContainer {
    override val recipeRepository: RecipeRepository by lazy {
        OfflineRecipeRepository(RecipeDatabase.getDatabase(context).recipeDao())
    }
}
