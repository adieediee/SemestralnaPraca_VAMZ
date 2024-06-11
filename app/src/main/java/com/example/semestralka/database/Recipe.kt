package com.example.semestralka.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Entita predstavujúca recept v databáze.
 *
 * @property id Unikátny identifikátor receptu. Automaticky generovaný.
 * @property name Názov receptu.
 * @property time Čas potrebný na prípravu receptu.
 * @property servings Počet porcií.
 * @property ingredients Zoznam ingrediencií potrebných na prípravu receptu.
 * @property type Typ receptu (napr. dezert, hlavné jedlo).
 * @property method Postup prípravy receptu.
 * @property isSelected Indikátor, či je recept označený ako vybraný. Predvolená hodnota je false.
 * @property imageUri URI obrázku receptu.
 */
@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: String,
    val servings: String,
    val ingredients: String,
    val type: String,
    val method: String,
    var isSelected: Boolean = false,
    val imageUri: String?
)
