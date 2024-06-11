import com.example.semestralka.database.NoteItem
import com.example.semestralka.database.NoteItemDao
import com.example.semestralka.database.NoteType

class NoteItemRepository(private val noteItemDao: NoteItemDao) {

    suspend fun getShoppingItems(): List<NoteItem> {
        return noteItemDao.getItemsByType(NoteType.SHOPPING)
    }

    suspend fun getCookDoItems(): List<NoteItem> {
        return noteItemDao.getItemsByType(NoteType.COOK_DO)
    }

    suspend fun addItem(item: NoteItem) {
        noteItemDao.insert(item)
    }

    suspend fun updateItem(item: NoteItem) {
        noteItemDao.update(item)
    }

    suspend fun deleteItem(item: NoteItem) {
        noteItemDao.delete(item)
    }
}
