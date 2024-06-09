package com.example.semestralka

import com.example.semestralka.database.Recipe

sealed interface RecipeEvent {
    object SaveRecipe: RecipeEvent
    data class SetName(val name: String) : RecipeEvent
    data class SetTime(val name: String) : RecipeEvent
    data class SetServings(val name: String) : RecipeEvent
    data class SetType(val name: String) : RecipeEvent
    data class SetIngredients(val name: String) : RecipeEvent
    data class SetMethod(val name: String) : RecipeEvent
    data class DeleteRecipe(val recipe: Recipe) : RecipeEvent

}