package com.example.semestralka.database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_items")
data class NoteItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isChecked: Boolean = false,
    val type: NoteType
)

enum class NoteType {
    SHOPPING, COOK_DO
}
