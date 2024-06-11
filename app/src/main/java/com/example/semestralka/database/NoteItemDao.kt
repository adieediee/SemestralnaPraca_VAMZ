import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteItemDao {
    @Query("SELECT * FROM note_items WHERE type = :type")
    suspend fun getItemsByType(type: NoteType): List<NoteItem>

    @Insert
    suspend fun insert(item: NoteItem)

    @Update
    suspend fun update(item: NoteItem)

    @Delete
    suspend fun delete(item: NoteItem)
}
