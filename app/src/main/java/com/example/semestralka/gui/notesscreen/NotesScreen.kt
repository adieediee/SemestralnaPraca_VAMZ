package com.example.semestralka.gui.notesscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.semestralka.R
import com.example.semestralka.navigation.NavigationDestination

object NotesDestination : NavigationDestination {
    override val route = "notes"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notes") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ShoppingList()
            Spacer(modifier = Modifier.height(16.dp))
            CookDoList()
        }
    }
}

@Composable
fun ShoppingList() {
    val items = remember { mutableStateListOf("90g prosciutto", "125g ball mozzarella", "750g minced beef", "200ml white sauce", "eggs") }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "shopping list",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { /* Add to shopping list */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "Add")
                }
            }
            Text(text = "today’s meal:", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(items) { item ->
                    CheckboxListItem(text = item)
                }
            }
            TextButton(onClick = { /* Add new item */ }) {
                Text(text = "add an item")
            }
        }
    }
}

@Composable
fun CookDoList() {
    val cookDoItems = remember { mutableStateListOf("prep the veggies", "restock spices", "meal prep for the weekend", "harvest some tomatoes") }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "today’s cook-dos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { /* Add cook-do item */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "Add")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(cookDoItems) { item ->
                    CheckboxListItem(text = item, checked = item == "prep the veggies" || item == "harvest some tomatoes")
                }
            }
            TextButton(onClick = { /* Add new cook-do */ }) {
                Text(text = "add a cook-do")
            }
        }
    }
}

@Composable
fun CheckboxListItem(text: String, checked: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null // This can be implemented as per requirement
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}


