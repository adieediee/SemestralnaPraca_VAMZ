package com.example.semestralka.gui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeInfoViewModel(private val recipeId: Int, private val repository: RecipeRepository) : ViewModel() {
    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    init {
        viewModelScope.launch {
            Log.d("RecipeInfoViewModel", "Loading recipe with ID: $recipeId")
            _recipe.value = repository.getRecipeById(recipeId)
            Log.d("RecipeInfoViewModel", "Loaded recipe: ${_recipe.value?.name}")
        }
    }
}

class RecipeInfoViewModelFactory(private val recipeId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeInfoViewModel(recipeId, ViewModelFactory.recipeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
