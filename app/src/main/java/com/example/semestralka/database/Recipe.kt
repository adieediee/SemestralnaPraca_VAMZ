package com.example.semestralka.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: String,
    val servings: String,
    val ingredients: String,
    val type: String,
    val method: String
)
