package com.example.semestralka.gui.notesscreen



import ViewModelFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TextButton
import androidx.compose.material3.Checkbox
import androidx.compose.ui.layout.ContentScale
import com.example.semestralka.database.NoteItem
import com.example.semestralka.database.NoteType


object NotesDestination : NavigationDestination {
    override val route = "notes"
}
@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
fun NotesScreen(viewModel: ShoppingListViewModel = viewModel(factory = ViewModelFactory), onNext: () -> Unit) {
    val shoppingItems by viewModel.shoppingItems.collectAsState()
    val cookDoItems by viewModel.cookDoItems.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.notes_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column {
            TopAppBar(
                title = { Text("Notes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                ),
                modifier = Modifier.background(Color.Transparent)
            )

            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
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
                                onDeleteItem = { item -> viewModel.deleteItem(item) },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ElevatedButton(
                                onClick = { viewModel.addItem(NoteItem(name = "New Item", type = NoteType.SHOPPING)) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Add an item")
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            CookDoListCard(
                                items = cookDoItems,
                                onItemCheckedChange = viewModel::onItemCheckedChange,
                                onUpdateItem = { oldItem, newItem -> viewModel.updateItem(oldItem, newItem) },
                                onDeleteItem = { item -> viewModel.deleteItem(item) },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ElevatedButton(
                                onClick = { viewModel.addItem(NoteItem(name = "New Cook-Do", type = NoteType.COOK_DO)) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Add a cook-do")
                            }
                        }
                    }
                }
            }

            BottomAppBar(
                modifier = Modifier
                    .height(60.dp)
                    .background(Color.Transparent),
                containerColor = Color.Transparent,
                contentColor = Color.Black
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
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShoppingListCard(
    items: List<NoteItem>,
    onItemCheckedChange: (NoteItem, Boolean) -> Unit,
    onUpdateItem: (NoteItem, NoteItem) -> Unit,
    onDeleteItem: (NoteItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(

        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF) // Correct color value
        ),

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
            items(items, key = { it.name }) { item ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            onDeleteItem(item)
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color.Transparent
                            null -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        )
                    },
                    dismissContent = {
                        EditableCheckboxListItem(
                            item.name, item.isChecked,
                            onCheckedChange = { isChecked -> onItemCheckedChange(item, isChecked) },
                            onUpdateText = { newText -> onUpdateItem(item, item.copy(name = newText)) },
                            onDeleteItem = { onDeleteItem(item) }
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CookDoListCard(
    items: List<NoteItem>,
    onItemCheckedChange: (NoteItem, Boolean) -> Unit,
    onUpdateItem: (NoteItem, NoteItem) -> Unit,
    onDeleteItem: (NoteItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF) // Correct color value
        )
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
            items(items, key = { it.name }) { item ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            onDeleteItem(item)
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color.Transparent
                            null -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        )
                    },
                    dismissContent = {
                        EditableCheckboxListItem(
                            item.name, item.isChecked,
                            onCheckedChange = { isChecked -> onItemCheckedChange(item, isChecked) },
                            onUpdateText = { newText -> onUpdateItem(item, item.copy(name = newText)) },
                            onDeleteItem = { onDeleteItem(item) }
                        )
                    }
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
            androidx.compose.material3.Text(
                text = text,
                modifier = Modifier
                    .weight(1f)
                    .clickable { isEditing = true }
            )
        }
    }
}