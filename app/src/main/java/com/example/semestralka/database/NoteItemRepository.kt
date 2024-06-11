import com.example.semestralka.database.NoteItem
import com.example.semestralka.database.NoteItemDao
import com.example.semestralka.database.NoteType
/**
 * Repozitár pre prístup k údajom položiek poznámok.
 *
 * @property noteItemDao DAO pre položky poznámok.
 */
class NoteItemRepository(private val noteItemDao: NoteItemDao) {

    /**
     * Získa zoznam položiek poznámok typu SHOPPING.
     *
     * @return Zoznam položiek poznámok typu SHOPPING.
     */
    suspend fun getShoppingItems(): List<NoteItem> {
        return noteItemDao.getItemsByType(NoteType.SHOPPING)
    }
    /**
     * Získa zoznam položiek poznámok typu COOK_DO.
     *
     * @return Zoznam položiek poznámok typu COOK_DO.
     */
    suspend fun getCookDoItems(): List<NoteItem> {
        return noteItemDao.getItemsByType(NoteType.COOK_DO)
    }
    /**
     * Pridá novú položku poznámky.
     *
     * @param item Položka poznámky, ktorá má byť pridaná.
     */
    suspend fun addItem(item: NoteItem) {
        noteItemDao.insert(item)
    }
    /**
     * Aktualizuje existujúcu položku poznámky.
     *
     * @param item Položka poznámky, ktorá má byť aktualizovaná.
     */
    suspend fun updateItem(item: NoteItem) {
        noteItemDao.update(item)
    }
    /**
     * Odstráni položku poznámky.
     *
     * @param item Položka poznámky, ktorá má byť odstránená.
     */
    suspend fun deleteItem(item: NoteItem) {
        noteItemDao.delete(item)
    }
}
