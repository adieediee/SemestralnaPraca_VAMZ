package com.example.semestralka.gui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
/**
 * ViewModel pre zoznam receptov s možnosťou vyhľadávania.
 * Robené s pomocou
 * https://amitshekhar.me/blog/instant-search-using-kotlin-flow-operators
 * @property repository Repozitár pre recepty.
 */
class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    val recipes: StateFlow<List<Recipe>> = _searchText
        .debounce(300) // optimalizacia
        .flatMapLatest { searchText ->
            if (searchText.isEmpty()) {
                repository.getAllRecipes()
            } else {
                repository.searchRecipesByName(searchText)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    /**
     * Reakcia na zmenu vyhľadávacieho textu.
     *
     * @param newText Nový vyhľadávací text.
     */
    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
    }
}
