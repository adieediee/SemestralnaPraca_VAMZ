package com.example.semestralka

import OfflineRecipeRepository
import android.app.Application
import com.example.semestralka.database.RecipeDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RecipeApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RecipeDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { OfflineRecipeRepository(database.recipeDao()) }

    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.init(repository)
    }
}
