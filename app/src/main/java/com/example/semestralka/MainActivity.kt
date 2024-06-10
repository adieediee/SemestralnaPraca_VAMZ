package com.example.semestralka

import OfflineRecipeRepository
import SharedViewModelMealCard
import ViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka.database.Recipe
import com.example.semestralka.database.RecipeDao
import com.example.semestralka.database.RecipeDatabase
import com.example.semestralka.database.RecipeRepository
import com.example.semestralka.gui.RecipeBookApp

import com.example.semestralka.ui.theme.SemestralkaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SemestralkaTheme {

                RecipeBookApp()
            }
        }


    }
}

