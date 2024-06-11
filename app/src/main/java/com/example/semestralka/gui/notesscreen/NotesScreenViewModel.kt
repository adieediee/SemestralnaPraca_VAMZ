package com.example.semestralka.gui.notesscreen

import NoteItemRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka.database.NoteItem
import com.example.semestralka.database.NoteType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch

/**
 * ViewModel pre správu nákupného zoznamu a zoznamu úloh pre varenie.
 *
 * @property repository Repozitár pre položky poznámok.
 */
class ShoppingListViewModel(private val repository: NoteItemRepository) : ViewModel() {

    private val _shoppingItems = MutableStateFlow<List<NoteItem>>(emptyList())
    val shoppingItems: StateFlow<List<NoteItem>> = _shoppingItems

    private val _cookDoItems = MutableStateFlow<List<NoteItem>>(emptyList())
    val cookDoItems: StateFlow<List<NoteItem>> = _cookDoItems

    private var itemCount = 0

    init {
        viewModelScope.launch {
            _shoppingItems.value = repository.getShoppingItems()
            _cookDoItems.value = repository.getCookDoItems()
        }
    }
    /**
     * Reakcia na zmenu stavu označenia položky.
     *
     * @param item Položka poznámky, ktorej stav sa mení.
     * @param isChecked Nový stav označenia položky.
     */
    fun onItemCheckedChange(item: NoteItem, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updateItem(item.copy(isChecked = isChecked))
            _shoppingItems.value = repository.getShoppingItems()
            _cookDoItems.value = repository.getCookDoItems()
        }
    }
    /**
     * Pridanie novej položky do zoznamu.
     *
     * @param item Položka poznámky, ktorá má byť pridaná.
     */
    fun addItem(item: NoteItem) {
        itemCount++
        val newItem = item.copy(name = "New Item $itemCount")
        viewModelScope.launch {
            repository.addItem(newItem)
            _shoppingItems.value = repository.getShoppingItems()
            _cookDoItems.value = repository.getCookDoItems()
        }
    }
    /**
     * Pridanie novej položky so špecifikovaným názvom do zoznamu.
     *
     * @param nameI Názov novej položky.
     */
    fun addItemWithName(nameI : String){
        itemCount++
        val newItem = NoteItem(name = nameI, type = NoteType.SHOPPING )
        viewModelScope.launch {
            repository.addItem(newItem)
            _shoppingItems.value = repository.getShoppingItems()
            _cookDoItems.value = repository.getCookDoItems()
        }
    }
    /**
     * Aktualizácia existujúcej položky v zozname.
     *
     * @param oldItem Pôvodná položka poznámky.
     * @param newItem Aktualizovaná položka poznámky.
     */
    fun updateItem(oldItem: NoteItem, newItem: NoteItem) {
        viewModelScope.launch {
            repository.updateItem(newItem)
            _shoppingItems.value = repository.getShoppingItems()
            _cookDoItems.value = repository.getCookDoItems()
        }
    }
    /**
     * Odstránenie položky zo zoznamu.
     *
     * @param item Položka poznámky, ktorá má byť odstránená.
     */
    fun deleteItem(item: NoteItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
            _shoppingItems.value = repository.getShoppingItems()
            _cookDoItems.value = repository.getCookDoItems()
        }
        if(itemCount > 0) {
            itemCount--
        }

    }
}


