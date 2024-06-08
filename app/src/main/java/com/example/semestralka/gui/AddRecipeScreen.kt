package com.example.semestralka.gui.addrecipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.semestralka.R
import com.example.semestralka.navigation.NavigationDestination

object AddRecipeDestination : NavigationDestination {
    override val route = "add_recipe"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var preparationTime by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf("") }
    var mealType by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Recipe") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Recipe Title", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Preparation Time (e.g., 2h 30min)", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
            TextField(
                value = preparationTime,
                onValueChange = { preparationTime = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Servings", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
            TextField(
                value = servings,
                onValueChange = { servings = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Meal Type (e.g., Dinner, Dessert)", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
            TextField(
                value = mealType,
                onValueChange = { mealType = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Ingredients (separated by commas)", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
            TextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Preparation Steps (each step on a new line)", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
            TextField(
                value = steps,
                onValueChange = { steps = it },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 10,
                minLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Handle form submission
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Save Recipe")
            }
        }
    }
}
