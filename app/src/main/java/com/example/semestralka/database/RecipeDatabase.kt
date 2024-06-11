package com.example.semestralka.database



import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
/**
 * Hlavná trieda databázy pre aplikáciu.
 * Túto časť som prebrala a upravila z codelabu
 * https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#4
 * @property recipeDao DAO pre recepty.
 * @property noteItemDao DAO pre položky poznámok.
 */
@Database(entities = [Recipe::class, NoteItem::class], version = 9, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun noteItemDao(): NoteItemDao
    companion object {

        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        /**
         * Získava inštanciu databázy.
         *
         * @param context Kontext aplikácie.
         * @param scope Korutína pre inicializáciu databázy.
         * @return Inštancia databázy.
         */
        fun getDatabase(context: Context, scope: CoroutineScope): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(RecipeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    /**
     * Callback trieda pre inicializáciu databázy.
     *
     * @property scope Korutína pre inicializáciu databázy.
     */
    private class RecipeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.recipeDao())
                }
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
        }
        /**
         * Naplní databázu počiatočnými receptami.
         *
         * @param recipeDao DAO pre recepty.
         */
        suspend fun populateDatabase(recipeDao: RecipeDao) {

            // Add initial recipes
            val recipes = listOf(
                Recipe(name = "Stuffed Peppers with cheese sauce", time = "1h 30min", servings = "4-6 servings", ingredients = "Bell peppers, cheese", type = "dinner", method = "Stuff and bake", imageUri = null),
                Recipe(name = "Easter cones as sweet nests", time = "2h", servings = "4-6 servings", ingredients = "Cones, sweets", type = "dessert", method = "Assemble and chill",imageUri = null),
                Recipe(name = "One-Pan Cheesy Sausage Gnocchi", time = "45min", servings = "4 servings", ingredients = "Gnocchi, sausage, cheese", type = "dinner", method = "Cook in one pan",imageUri = null),
                Recipe(name = "Strawberry Mochi cakes", time = "50min", servings = "8 servings", ingredients = "Strawberries, mochi", type = "dessert", method = "Bake and assemble",imageUri = null),
                Recipe(name = "Homemade Lasagna", time = "2h", servings = "8 servings", ingredients = "Lasagna noodles, cheese, sauce", type = "dinner", method = "Layer and bake",imageUri = null)
            )
            recipes.forEach {
                recipeDao.insert(it)
            }

        }
    }

}



