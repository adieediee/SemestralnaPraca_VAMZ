package com.example.semestralka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

