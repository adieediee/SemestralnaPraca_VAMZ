import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeDao
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.Flow

class OfflineRecipeRepository(private val recipeDao: RecipeDao) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    override suspend fun getRecipeById(id: Int): Recipe? = recipeDao.getRecipeById(id)

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.update(recipe)
    }

    override suspend fun getSelectedRecipe(): Recipe? {
        return recipeDao.getSelectedRecipe()
    }

    override suspend fun selectRecipe(recipe: Recipe) {
        recipeDao.clearSelectedRecipe()
        recipe.isSelected = true
        recipeDao.update(recipe)
    }

    override fun searchRecipesByName(searchText: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipesByName(searchText)
    }
}