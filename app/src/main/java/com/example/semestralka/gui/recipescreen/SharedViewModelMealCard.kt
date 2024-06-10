package com.example.semestralka.gui.recipescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModelMealCard(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> get() = _selectedRecipe

    init {
        viewModelScope.launch {
            _selectedRecipe.value = recipeRepository.getSelectedRecipe()
        }
    }

    fun selectRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.selectRecipe(recipe)
            _selectedRecipe.value = recipe
        }
    }
}