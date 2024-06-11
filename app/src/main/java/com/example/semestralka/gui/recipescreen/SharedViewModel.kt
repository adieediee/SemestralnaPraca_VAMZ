package com.example.semestralka

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
/**
 * Zdieľaný ViewModel pre správu vybraného receptu.
 *
 * @property repository Repozitár pre recepty.
 */
class SharedViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> get() = _selectedRecipe
    /**
     * Vyberie recept a nastaví ho ako aktuálne vybraný.
     *
     * @param recipe Recept, ktorý má byť vybraný.
     */
    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
    }
    /**
     * Odstráni recept podľa jeho ID.
     *
     * @param recipeId ID receptu, ktorý má byť odstránený.
     */
    fun deleteRecipe(recipeId: Int) {
        viewModelScope.launch {
            repository.deleteRecipeById(recipeId)
        }
    }
}
