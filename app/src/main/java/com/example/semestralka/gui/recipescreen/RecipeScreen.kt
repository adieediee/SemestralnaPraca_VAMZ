package com.example.semestralka.gui

import ViewModelFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.semestralka.R
import com.example.semestralka.SharedViewModel
import com.example.semestralka.database.Recipe
import com.example.semestralka.navigation.NavigationDestination

object RecipeDestination : NavigationDestination {
    override val route = "recipes"
}

@Composable
fun RecipeScreen(
    onRecipeClick: () -> Unit,
    onPrevious: () -> Unit,
    onAdd: () -> Unit,
    sharedViewModel: SharedViewModel,
    viewModel: RecipeListViewModel = viewModel(factory = ViewModelFactory)
) {
    val searchText by viewModel.searchText.collectAsState()
    val recipes by viewModel.recipes.collectAsState()

    Log.d("RecipeScreen", "Loaded recipes: ${recipes.size}")
    recipes.forEach { recipe ->
        Log.d("RecipeScreen", "Recipe: ${recipe.name}, ID: ${recipe.id}")
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(50.dp)
                    .background(Color.Transparent),
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
            ) {
                val iconModifier = Modifier.size(60.dp)
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Previous",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onPrevious)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onAdd() }, modifier = iconModifier) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add_recipe),
                        contentDescription = "Add",
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F0F0))
        ) {
            SearchBar(searchText, onSearchTextChanged = { viewModel.onSearchTextChanged(it) })
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp), // Added spacing between items
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp) // Added padding for the whole list
            ) {
                items(recipes) { recipe ->
                    RecipeCard(recipe, onClick = {
                        sharedViewModel.selectRecipe(recipe)
                        onRecipeClick()
                    })
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp), // Added padding around each card
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(recipe.imageUri ?: R.drawable.default_image_recipe)
                .crossfade(true)
                .placeholder(R.drawable.default_image_recipe)
                .error(R.drawable.default_image_recipe)
                .build()

            val painter = rememberAsyncImagePainter(model = imageRequest)

            Image(
                painter = painter,
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = recipe.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = recipe.type,
                    color = Color(0xFFFAAED2),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Time",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = recipe.time,
                        fontSize = 14.sp,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_servings),
                        contentDescription = "Servings",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = recipe.servings,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.Transparent),

        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .padding(8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
        ) {
            TextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                placeholder = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ), // Optional: background color and shape
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}
