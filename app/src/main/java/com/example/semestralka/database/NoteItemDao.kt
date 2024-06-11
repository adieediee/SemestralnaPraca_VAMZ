package com.example.semestralka.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
/**
 * Získa zoznam položiek poznámok podľa zadaného typu.
 *
 * @param type Typ položky poznámky.
 * @return Zoznam položiek poznámok daného typu.
 */
@Dao
interface NoteItemDao {
    /**
     * Získa zoznam položiek poznámok podľa zadaného typu.
     *
     * @param type Typ položky poznámky.
     * @return Zoznam položiek poznámok daného typu.
     */
    @Query("SELECT * FROM note_items WHERE type = :type")
    suspend fun getItemsByType(type: NoteType): List<NoteItem>
    /**
     * Vloží novú položku poznámky do databázy. Ak položka už existuje, nahradí ju.
     *
     * @param item Položka poznámky, ktorá má byť vložená.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: NoteItem)
    /**
     * Aktualizuje existujúcu položku poznámky v databáze.
     *
     * @param item Položka poznámky, ktorá má byť aktualizovaná.
     */
    @Update
    suspend fun update(item: NoteItem)
    /**
     * Odstráni položku poznámky z databázy.
     *
     * @param item Položka poznámky, ktorá má byť odstránená.
     */
    @Delete
    suspend fun delete(item: NoteItem)
}
