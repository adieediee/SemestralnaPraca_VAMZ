package com.example.semestralka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.semestralka.navigation.Navigation
/**
 * Hlavná kompozitná funkcia aplikácie RecipeBookApp.
 *
 * @param navController Navigačný ovládač pre navigáciu medzi obrazovkami, predvolená hodnota je nová inštancia navigačného ovládača.
 */
@Composable
fun RecipeBookApp(navController: NavHostController = rememberNavController()) {
    Navigation(navController = navController)
}