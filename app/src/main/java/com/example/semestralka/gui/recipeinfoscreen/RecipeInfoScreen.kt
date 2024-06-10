package com.example.semestralka.gui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.semestralka.R
import com.example.semestralka.SharedViewModel
import com.example.semestralka.database.Recipe
import com.example.semestralka.gui.recipescreen.SharedViewModelMealCard
import com.example.semestralka.navigation.NavigationDestination

object RecipeInfoDestination : NavigationDestination {
    override val route = "recipeInfo"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeInfoScreen(
    onBack: () -> Unit,
    onEdit: () -> Unit,
    sharedViewModel: SharedViewModel,
    sharedViewModelMealCard: SharedViewModelMealCard
) {
    val recipe by sharedViewModel.selectedRecipe.collectAsState()

    recipe?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(it.imageUri ?: R.drawable.default_image_recipe)
                    .crossfade(true)
                    .placeholder(R.drawable.default_image_recipe)
                    .error(R.drawable.default_image_recipe)
                    .build()

                AsyncImage(
                    model = imageRequest,
                    contentDescription = it.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_time), contentDescription = "Time")
                        Text(text = it.time)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_servings), contentDescription = "Servings")
                        Text(text = it.servings)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_meal), contentDescription = "Type")
                        Text(text = it.type)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                IngredientsList(it.ingredients)
                Spacer(modifier = Modifier.height(16.dp))
                RecipeSteps(it.method)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { sharedViewModelMealCard.selectRecipe(it) }) {
                    Text("Select for Today")
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_back), // Replace with your back image resource
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_edit), // Replace with your edit image resource
                contentDescription = "Edit",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onEdit)
            )
        }
    }
}

@Composable
fun IngredientsList(ingredients: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ingredients:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val ingredientList = ingredients.split(", ")
            ingredientList.forEach { ingredient ->
                CheckboxListItemRecipe(text = ingredient)
            }
            TextButton(onClick = { /* Add to shopping list */ }) {
                Text(text = "Add to shopping list")
            }
        }
    }
}

@Composable
fun RecipeSteps(method: String) {
    val steps = method.split(". ")
    Column {
        steps.forEachIndexed { index, step ->
            Text(
                text = "STEP ${index + 1}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(text = step, fontSize = 16.sp)
        }
    }
}

@Composable
fun CheckboxListItemRecipe(text: String, checked: Boolean = false) {
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
