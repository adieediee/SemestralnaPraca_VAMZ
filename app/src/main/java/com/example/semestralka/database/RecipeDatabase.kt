package com.example.semestralka.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Recipe::class], version = 5, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {

        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RecipeDatabase {
            Log.d("RecipeDatabase", "getDatabase called")
            return INSTANCE ?: synchronized(this) {
                Log.d("RecipeDatabase", "Creating new instance of database")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(RecipeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                Log.d("RecipeDatabase", "Database instance created")
                instance
            }
        }
    }

    private class RecipeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RecipeDatabase", "Database onCreate called")
            INSTANCE?.let { database ->
                scope.launch {
                    Log.d("RecipeDatabase", "Launching coroutine to populate database")
                    populateDatabase(database.recipeDao())
                }
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            Log.d("RecipeDatabase", "Database onOpen called")
        }

        suspend fun populateDatabase(recipeDao: RecipeDao) {
            Log.d("RecipeDatabase", "Populating database with initial recipes")
            // Add initial recipes
            val recipes = listOf(
                Recipe(name = "Stuffed Peppers with cheese sauce", time = "1h 30min", servings = "4-6 servings", ingredients = "Bell peppers, cheese", type = "dinner", method = "Stuff and bake"),
                Recipe(name = "Easter cones as sweet nests", time = "2h", servings = "4-6 servings", ingredients = "Cones, sweets", type = "dessert", method = "Assemble and chill"),
                Recipe(name = "One-Pan Cheesy Sausage Gnocchi", time = "45min", servings = "4 servings", ingredients = "Gnocchi, sausage, cheese", type = "dinner", method = "Cook in one pan"),
                Recipe(name = "Strawberry Mochi cakes", time = "50min", servings = "8 servings", ingredients = "Strawberries, mochi", type = "dessert", method = "Bake and assemble"),
                Recipe(name = "Homemade Lasagna", time = "2h", servings = "8 servings", ingredients = "Lasagna noodles, cheese, sauce", type = "dinner", method = "Layer and bake")
            )
            recipes.forEach {
                Log.d("RecipeDatabase", "Inserting recipe: ${it.name}")
                recipeDao.insert(it)
                Log.d("RecipeDatabase", "Inserted recipe: ${it.name}")
            }
            Log.d("RecipeDatabase", "Finished populating database")
        }
    }

}

// Add this simple logging test to your MainActivity or Application class to verify logging works.

