import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka.database.RecipeRepository
import com.example.semestralka.gui.AddRecipeViewModel
import com.example.semestralka.gui.RecipeListViewModel


object ViewModelFactory : ViewModelProvider.Factory {

    private lateinit var application: Application
    lateinit var recipeRepository: RecipeRepository


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddRecipeViewModel::class.java) -> {
                AddRecipeViewModel(recipeRepository) as T
            }
            modelClass.isAssignableFrom(RecipeListViewModel::class.java) -> {
                RecipeListViewModel(recipeRepository) as T
            }
            modelClass.isAssignableFrom(SharedViewModelMealCard::class.java) -> {
                SharedViewModelMealCard(application, recipeRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun init(repository: RecipeRepository,app: Application) {
        recipeRepository = repository
        application = app
    }
}