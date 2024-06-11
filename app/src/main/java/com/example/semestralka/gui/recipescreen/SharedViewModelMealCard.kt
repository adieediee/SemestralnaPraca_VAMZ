import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka.MainActivity
import com.example.semestralka.R
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
/**
 * Zdieľaný ViewModel pre správu vybranej receptovej karty s notifikáciami.
 *
 * @param application Aplikácia.
 * @param recipeRepository Repozitár pre recepty.
 */
class SharedViewModelMealCard(application: Application, private val recipeRepository: RecipeRepository) : AndroidViewModel(application) {
    /**
     * Vyberie recept a nastaví ho ako aktuálne vybraný, zároveň odošle notifikáciu.
     *
     * @param recipe Recept, ktorý má byť vybraný.
     */
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> get() = _selectedRecipe

    init {
        viewModelScope.launch {
            _selectedRecipe.value = recipeRepository.getSelectedRecipe()
        }
    }
    /**
     * Odošle notifikáciu s daným titulkom a správou.
     *
     * @param context Kontext aplikácie.
     * @param title Titulok notifikácie.
     * @param message Správa notifikácie.
     */
    fun selectRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.selectRecipe(recipe)
            _selectedRecipe.value = recipe
            sendNotification(getApplication(), "Recipe Selected", "You have selected the recipe: ${recipe.name}")
        }
    }
    /**
     * Odošle notifikáciu s daným názvom a správou.
     * Robená podľa codelabu
     *https://developer.android.com/develop/ui/views/notifications/build-notification
     * @param context Kontext aplikácie.
     * @param title Názov notifikácie.
     * @param message Správa notifikácie.
     */
    private fun sendNotification(context: Context, title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                return
            }
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_meal)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }

    }
}
