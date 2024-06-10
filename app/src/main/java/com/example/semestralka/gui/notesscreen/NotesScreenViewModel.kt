import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShoppingListViewModel : ViewModel() {

    private val _shoppingItems = MutableStateFlow(
        listOf(
            ShoppingItem("90g prosciutto"),
            ShoppingItem("125g ball mozzarella"),
            ShoppingItem("750g minced beef"),
            ShoppingItem("200ml white sauce"),
        )
    )
    val shoppingItems: StateFlow<List<ShoppingItem>> = _shoppingItems

    private val _cookDoItems = MutableStateFlow(
        listOf(
            ShoppingItem("Prep the veggies"),
            ShoppingItem("Restock spices"),
            ShoppingItem("Meal prep for the weekend"),
            ShoppingItem("Harvest some tomatoes")
        )
    )
    val cookDoItems: StateFlow<List<ShoppingItem>> = _cookDoItems

    fun onItemCheckedChange(item: ShoppingItem, isChecked: Boolean) {
        _shoppingItems.value = _shoppingItems.value.map {
            if (it == item) it.copy(isChecked = isChecked) else it
        }
    }

    fun onCookDoItemCheckedChange(item: ShoppingItem, isChecked: Boolean) {
        _cookDoItems.value = _cookDoItems.value.map {
            if (it == item) it.copy(isChecked = isChecked) else it
        }
    }

    fun addItem(item: ShoppingItem) {
        _shoppingItems.value = _shoppingItems.value + item
    }

    fun addCookDoItem(item: ShoppingItem) {
        _cookDoItems.value = _cookDoItems.value + item
    }

    fun updateItem(oldItem: ShoppingItem, newItem: ShoppingItem) {
        _shoppingItems.value = _shoppingItems.value.map {
            if (it == oldItem) newItem else it
        }
    }

    fun updateCookDoItem(oldItem: ShoppingItem, newItem: ShoppingItem) {
        _cookDoItems.value = _cookDoItems.value.map {
            if (it == oldItem) newItem else it
        }
    }
    fun deleteItem(item: ShoppingItem) {
        _shoppingItems.value = _shoppingItems.value - item
    }
}

data class ShoppingItem(val name: String, val isChecked: Boolean = false)
