package com.example.semestralka.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE id = :id LIMIT 1")
    suspend fun getRecipeById(id: Int): Recipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)
    @Query("SELECT * FROM recipe WHERE isSelected = 1 LIMIT 1")
    suspend fun getSelectedRecipe(): Recipe?

    @Query("UPDATE recipe SET isSelected = 0")
    suspend fun clearSelectedRecipe()
}
