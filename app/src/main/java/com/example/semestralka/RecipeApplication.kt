package com.example.semestralka

import OfflineRecipeRepository
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.semestralka.database.RecipeDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RecipeApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RecipeDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { OfflineRecipeRepository(database.recipeDao()) }

    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.init(repository,this)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
