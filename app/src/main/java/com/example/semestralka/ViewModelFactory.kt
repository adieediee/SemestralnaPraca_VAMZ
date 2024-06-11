import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka.SharedViewModel
import com.example.semestralka.database.RecipeRepository
import com.example.semestralka.gui.AddRecipeViewModel
import com.example.semestralka.gui.RecipeListViewModel
import com.example.semestralka.gui.notesscreen.ShoppingListViewModel

/**
 * Objekt ViewModelFactory pre vytváranie inštancií ViewModelov.
 */
object ViewModelFactory : ViewModelProvider.Factory {

    private lateinit var application: Application
    lateinit var recipeRepository: RecipeRepository
    lateinit var noteRepository: NoteItemRepository
    /**
     * Vytvára inštanciu ViewModelu podľa zadaného modelClass.
     *
     * @param modelClass Trieda ViewModelu, ktorý má byť vytvorený.
     * @return Inštancia ViewModelu.
     */
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
            modelClass.isAssignableFrom(SharedViewModel::class.java) -> {
                SharedViewModel(recipeRepository) as T
            }modelClass.isAssignableFrom(ShoppingListViewModel::class.java) -> {
                ShoppingListViewModel(noteRepository) as T
            }
            else -> throw IllegalArgumentException()
        }
    }
    /**
     * Inicializuje ViewModelFactory s potrebnými závislosťami.
     *
     * @param repository Repozitár pre recepty.
     * @param app Aplikácia.
     * @param noteItemRepository Repozitár pre položky poznámok.
     */
    fun init(repository: RecipeRepository,app: Application,noteItemRepository : NoteItemRepository) {
        recipeRepository = repository
        application = app
        noteRepository = noteItemRepository
    }
}