package com.example.semestralka.gui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.launch
/**
 * ViewModel pre pridanie alebo úpravu receptu.
 *
 * @param repository Repozitár pre recepty.
 */
class AddRecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    var name by mutableStateOf("")
    var time by mutableStateOf("")
    var servings by mutableStateOf("")
    var ingredients by mutableStateOf("")
    var type by mutableStateOf("")
    var method by mutableStateOf("")
    var imageUri by mutableStateOf<String?>(null)
    /**
     * Načítanie receptu podľa ID a nastavenie jeho údajov do stavov ViewModelu.
     *
     * @param recipeId ID receptu, ktorý má byť načítaný.
     */
    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = repository.getRecipeById(recipeId)
            recipe?.let {
                name = it.name
                time = it.time
                servings = it.servings
                ingredients = it.ingredients
                type = it.type
                method = it.method
                imageUri = it.imageUri
            }
        }
    }
    /**
     * Uloženie receptu s aktuálnymi údajmi v ViewModeli.
     *
     * @param recipeId ID receptu, ak ide o úpravu existujúceho receptu, inak null.
     * @param onComplete Lambda funkcia, ktorá sa vykoná po dokončení uloženia.
     */
    fun saveRecipe(recipeId: Int?, onComplete: () -> Unit) {
        val recipe = Recipe(
            id = recipeId ?: 0,
            name = name,
            time = time,
            servings = servings,
            ingredients = ingredients,
            type = type,
            method = method,
            imageUri = imageUri
        )
        viewModelScope.launch {
            if (recipeId == null) {
                repository.insertRecipe(recipe)
            } else {
                repository.updateRecipe(recipe.copy(id = recipeId))
            }
            onComplete()
        }
    }
}
