package com.example.semestralka.gui.mainscreen

import ShoppingItem
import ShoppingListViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
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
    val cookDoItems by viewModel.cookDoItems.collectAsState()

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
                    painter = painterResource(id = R.drawable.ic_forward),
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
                        onDeleteItem = { item -> viewModel.deleteItem(item) }, // Pass the delete action
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
                        onItemCheckedChange = viewModel::onCookDoItemCheckedChange,
                        onUpdateItem = { oldItem, newItem -> viewModel.updateCookDoItem(oldItem, newItem) },
                        onDeleteItem = { item -> viewModel.deleteItem(item) }, // Pass the delete action
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ElevatedButton(
                        onClick = { viewModel.addCookDoItem(ShoppingItem("New cook do")) },
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
    onDeleteItem: (ShoppingItem) -> Unit,
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
                    text = "Shopping List",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Today's meal:", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(items) { item ->
                EditableCheckboxListItem(
                    item.name, item.isChecked,
                    onCheckedChange = { isChecked -> onItemCheckedChange(item, isChecked) },
                    onUpdateText = { newText -> onUpdateItem(item, item.copy(name = newText)) },
                    onDeleteItem = { onDeleteItem(item) }
                )
            }
        }
    }
}

@Composable
fun CookDoListCard(
    items: List<ShoppingItem>,
    onItemCheckedChange: (ShoppingItem, Boolean) -> Unit,
    onUpdateItem: (ShoppingItem, ShoppingItem) -> Unit,
    onDeleteItem: (ShoppingItem) -> Unit,
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
                    text = "Cook Do List",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Today's cook dos:", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(items) { item ->
                EditableCheckboxListItem(
                    item.name, item.isChecked,
                    onCheckedChange = { isChecked -> onItemCheckedChange(item, isChecked) },
                    onUpdateText = { newText -> onUpdateItem(item, item.copy(name = newText)) },
                    onDeleteItem = { onDeleteItem(item) }
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
    onUpdateText: (String) -> Unit,
    onDeleteItem: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editText by remember { mutableStateOf(text) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Delete Item") },
            text = { Text(text = "Are you sure you want to delete this item?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteItem()
                    showDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDialog = true
                    }
                )
            }
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
