package com.example.semestralka.gui.mainscreen

import ShoppingItem
import ShoppingListViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestralka.R
import com.example.semestralka.navigation.NavigationDestination

object NotesDestination : NavigationDestination {
    override val route = "notes"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: ShoppingListViewModel = viewModel(), onNext: () -> Unit) {
    val shoppingItems by viewModel.shoppingItems.collectAsState()
    val cookDoItems = remember { mutableStateListOf("prep the veggies", "restock spices", "meal prep for the weekend", "harvest some tomatoes") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes") }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(60.dp),
                containerColor = Color.Transparent
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_forward), // Replace with your edit image resource
                    contentDescription = "Next",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onNext)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    ShoppingListCard(
                        items = shoppingItems,
                        onItemCheckedChange = viewModel::onItemCheckedChange,
                        onUpdateItem = { oldItem, newItem -> viewModel.updateItem(oldItem, newItem) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ElevatedButton(
                        onClick = { viewModel.addItem(ShoppingItem("New item")) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Add an item")
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    CookDoListCard(
                        items = cookDoItems,
                        onUpdateItem = { index, newText -> cookDoItems[index] = newText },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ElevatedButton(
                        onClick = { cookDoItems.add("New cook-do") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Add a cook-do")
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListCard(
    items: List<ShoppingItem>,
    onItemCheckedChange: (ShoppingItem, Boolean) -> Unit,
    onUpdateItem: (ShoppingItem, ShoppingItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                Text(
                    text = "shopping list",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "today’s meal:", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(items) { item ->
                EditableCheckboxListItem(
                    item.name, item.isChecked,
                    onCheckedChange = { isChecked -> onItemCheckedChange(item, isChecked) },
                    onUpdateText = { newText -> onUpdateItem(item, item.copy(name = newText)) }
                )
            }
        }
    }
}

@Composable
fun CookDoListCard(
    items: List<String>,
    onUpdateItem: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                Text(
                    text = "today’s cook-dos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(items.size) { index ->
                val item = items[index]
                var isChecked by remember { mutableStateOf(item == "harvest some tomatoes") }
                EditableCheckboxListItem(
                    text = item,
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    onUpdateText = { newText -> onUpdateItem(index, newText) }
                )
            }
        }
    }
}

@Composable
fun EditableCheckboxListItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onUpdateText: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editText by remember { mutableStateOf(text) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        if (isEditing) {
            TextField(
                value = editText,
                onValueChange = { editText = it },
                keyboardActions = KeyboardActions(
                    onDone = {
                        onUpdateText(editText)
                        isEditing = false
                    }
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        } else {
            Text(
                text = text,
                modifier = Modifier
                    .weight(1f)
                    .clickable { isEditing = true }
            )
        }
    }
}
