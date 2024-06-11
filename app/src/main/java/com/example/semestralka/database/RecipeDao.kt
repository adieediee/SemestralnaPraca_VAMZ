package com.example.semestralka.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
/**
 * DAO rozhranie pre prístup k údajom receptov v databáze.
 */
@Dao
interface RecipeDao {

    /**
     * Získa všetky recepty.
     *
     * @return Tok obsahujúci zoznam všetkých receptov.
     */
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>
    /**
     * Získa recept podľa jeho ID.
     *
     * @param id ID receptu.
     * @return Recept s daným ID alebo null, ak recept neexistuje.
     */
    @Query("SELECT * FROM recipe WHERE id = :id LIMIT 1")
    suspend fun getRecipeById(id: Int): Recipe?
    /**
     * Vloží nový recept do databázy. Ak recept už existuje, nahradí ho.
     *
     * @param recipe Recept, ktorý má byť vložený.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)
    /**
     * Aktualizuje existujúci recept v databáze.
     *
     * @param recipe Recept, ktorý má byť aktualizovaný.
     */
    @Update
    suspend fun update(recipe: Recipe)
    /**
     * Odstráni recept z databázy.
     *
     * @param recipe Recept, ktorý má byť odstránený.
     */
    @Delete
    suspend fun delete(recipe: Recipe)
    /**
     * Získa vybraný recept.
     *
     * @return Vybraný recept alebo null, ak žiaden recept nie je vybraný.
     */
    @Query("SELECT * FROM recipe WHERE isSelected = 1 LIMIT 1")
    suspend fun getSelectedRecipe(): Recipe?
    /**
     * Vymaže všetky označenia receptov ako vybraných.
     */
    @Query("UPDATE recipe SET isSelected = 0")
    suspend fun clearSelectedRecipe()
    /**
     * Vyhľadáva recepty podľa názvu.
     *
     * @param searchText Text na vyhľadávanie receptov.
     * @return Tok obsahujúci zoznam receptov, ktoré zodpovedajú hľadanému textu.
     */
    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :searchText || '%' ORDER BY name ASC")
    fun searchRecipesByName(searchText: String): Flow<List<Recipe>>
    /**
     * Odstráni recept podľa jeho ID.
     *
     * @param recipeId ID receptu, ktorý má byť odstránený.
     */
    @Query("DELETE FROM recipe WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)
}
