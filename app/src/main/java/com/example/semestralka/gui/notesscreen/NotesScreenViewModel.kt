import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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

    fun onItemCheckedChange(item: ShoppingItem, isChecked: Boolean) {
        _shoppingItems.value = _shoppingItems.value.map {
            if (it == item) it.copy(isChecked = isChecked) else it
        }
    }

    fun addItem(item: ShoppingItem) {
        _shoppingItems.value = _shoppingItems.value + item
    }


    fun updateItem(oldItem: ShoppingItem, newItem: ShoppingItem) {
        _shoppingItems.value = _shoppingItems.value.map {
            if (it == oldItem) newItem else it
        }
    }
}

data class ShoppingItem(val name: String, val isChecked: Boolean = false)
