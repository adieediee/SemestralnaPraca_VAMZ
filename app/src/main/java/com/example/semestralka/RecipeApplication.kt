package com.example.semestralka

import android.app.Application
import com.example.semestralka.database.RecipeDatabase
import com.example.semestralka.database.OfflineRecipeRepository

class RecipeApplication : Application() {
    val database by lazy { RecipeDatabase.getDatabase(this) }
    val repository by lazy { OfflineRecipeRepository(database.recipeDao()) }

    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.init(repository)
    }
}
