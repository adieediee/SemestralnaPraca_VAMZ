import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeDao
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.Flow
/**
 * Offline implementácia repozitára pre recepty.
 *
 * @property recipeDao DAO pre recepty.
 */
class OfflineRecipeRepository(private val recipeDao: RecipeDao) : RecipeRepository {
    /**
     * Získa všetky recepty.
     *
     * @return Tok obsahujúci zoznam všetkých receptov.
     */
    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()
    /**
     * Získa recept podľa jeho ID.
     *
     * @param id ID receptu.
     * @return Recept s daným ID alebo null, ak recept neexistuje.
     */
    override suspend fun getRecipeById(id: Int): Recipe? = recipeDao.getRecipeById(id)
    /**
     * Vloží nový recept.
     *
     * @param recipe Recept, ktorý má byť vložený.
     */
    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insert(recipe)
    }
    /**
     * Odstráni recept.
     *
     * @param recipe Recept, ktorý má byť odstránený.
     */
    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.delete(recipe)
    }
    /**
     * Aktualizuje existujúci recept.
     *
     * @param recipe Recept, ktorý má byť aktualizovaný.
     */
    override suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.update(recipe)
    }
    /**
     * Získa vybraný recept.
     *
     * @return Vybraný recept alebo null, ak žiaden recept nie je vybraný.
     */
    override suspend fun getSelectedRecipe(): Recipe? {
        return recipeDao.getSelectedRecipe()
    }
    /**
     * Označí recept ako vybraný.
     *
     * @param recipe Recept, ktorý má byť označený ako vybraný.
     */
    override suspend fun selectRecipe(recipe: Recipe) {
        recipeDao.clearSelectedRecipe()
        recipe.isSelected = true
        recipeDao.update(recipe)
    }
    /**
     * Vyhľadáva recepty podľa názvu.
     *
     * @param searchText Text na vyhľadávanie receptov.
     * @return Tok obsahujúci zoznam receptov, ktoré zodpovedajú hľadanému textu.
     */
    override fun searchRecipesByName(searchText: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipesByName(searchText)
    }
    /**
     * Odstráni recept podľa jeho ID.
     *
     * @param recipeId ID receptu, ktorý má byť odstránený.
     */
    override suspend fun deleteRecipeById(recipeId: Int) {
        recipeDao.deleteRecipeById(recipeId)
    }
}