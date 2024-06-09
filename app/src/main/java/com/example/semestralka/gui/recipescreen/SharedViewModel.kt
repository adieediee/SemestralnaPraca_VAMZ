package com.example.semestralka

import androidx.lifecycle.ViewModel
import com.example.semestralka.database.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> get() = _selectedRecipe

    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
    }
}
