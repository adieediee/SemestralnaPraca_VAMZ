package com.example.semestralka.gui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    var name by mutableStateOf("")
    var time by mutableStateOf("")
    var servings by mutableStateOf("")
    var ingredients by mutableStateOf("")
    var type by mutableStateOf("")
    var method by mutableStateOf("")

    fun insertRecipe(onRecipeInserted: () -> Unit) {
        val recipe = Recipe(
            name = name,
            time = time,
            servings = servings,
            ingredients = ingredients,
            type = type,
            method = method
        )
        viewModelScope.launch {
            repository.insertRecipe(recipe)
            onRecipeInserted()
        }
    }
}
