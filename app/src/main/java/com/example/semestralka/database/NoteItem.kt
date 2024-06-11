package com.example.semestralka.database
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Entita predstavujúca položku poznámky v databáze.
 *
 * @property id Unikátny identifikátor položky poznámky. Automaticky generovaný.
 * @property name Názov alebo popis položky poznámky.
 * @property isChecked Stav indikujúci, či je položka poznámky označená alebo nie. Predvolená hodnota je false.
 * @property type Typ položky poznámky, definovaný v enum triede [NoteType].
 */
@Entity(tableName = "note_items")
data class NoteItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isChecked: Boolean = false,
    val type: NoteType
)

/**
 * Enum trieda predstavujúca typy položiek poznámok.
 *
 * @property SHOPPING Položka poznámky týkajúca sa nákupu.
 * @property COOK_DO Položka poznámky týkajúca sa varenia alebo úloh na vykonanie.
 */
enum class NoteType {
    SHOPPING, COOK_DO
}
