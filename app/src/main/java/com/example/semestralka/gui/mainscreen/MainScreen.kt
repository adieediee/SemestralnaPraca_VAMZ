package com.example.semestralka.gui

import SharedViewModelMealCard
import ShoppingItem
import ShoppingListViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.semestralka.R

import com.example.semestralka.navigation.NavigationDestination

object MainDestination : NavigationDestination {
    override val route = "main"
}

@Composable
fun MainScreen(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    viewModel: ShoppingListViewModel,
    sharedViewModelMealCard: SharedViewModelMealCard
) {
    Scaffold(bottomBar = {
        BottomAppBar(
            modifier = Modifier.height(60.dp),
            containerColor = Color.Transparent
        ) {
            val modifier = Modifier.padding(5.dp)
            Image(
                painter = painterResource(id = R.drawable.ic_back), // Replace with your edit image resource
                contentDescription = "Previous",
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onPrevious)
            )
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
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.here_s_to_a_delicious_meal),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(vertical = 8.dp),
                    lineHeight = 40.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.stars), // replace with your star drawable
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(72.dp) // Adjust size as needed
                        .offset(x = (-16).dp, y = 1.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = "today’s meal choice",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            MealCard(sharedViewModelMealCard)
            Spacer(modifier = Modifier.height(16.dp))
            ListsRow(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MealCard(sharedViewModelMealCard: SharedViewModelMealCard) {
    val selectedRecipe by sharedViewModelMealCard.selectedRecipe.collectAsState()

    Card(
        shape = RoundedCornerShape(16.dp),
        //elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent, //Card background color

        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .height(240.dp)
            .background(Color.Transparent)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.gradient_background), // Your gradient background image
                contentDescription = null,
                contentScale = ContentScale.FillBounds,

                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                selectedRecipe?.let { recipe ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_meal), // Replace with your meal icon resource
                            contentDescription = "Meal Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = recipe.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    val imageRequest = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.imageUri ?: R.drawable.default_image_recipe)
                        .crossfade(true)
                        .placeholder(R.drawable.default_image_recipe)
                        .error(R.drawable.default_image_recipe)
                        .build()

                    AsyncImage(
                        model = imageRequest,
                        contentDescription = recipe.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                } ?: run {
                    Text(
                        text = "No recipe selected",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}




@Composable
fun ListsRow(viewModel: ShoppingListViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ShoppingListCard(viewModel)
        CookDoListCard(viewModel)
    }
}

@Composable
fun ShoppingListCard(viewModel: ShoppingListViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp / 2 - 24.dp
    val shoppingItems by viewModel.shoppingItems.collectAsState()

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(4.dp)
            .width(screenWidthDp)
            .height(293.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF6F6F6)
        )
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_shopping), // replace with your shopping icon drawable
                        contentDescription = "Shopping Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "shopping list", fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "today’s meal:")
            }
            items(shoppingItems) { item ->
                CheckboxListItem(item.name, item.isChecked) {
                    viewModel.onItemCheckedChange(item, it)
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "other:")
                CheckboxListItem("eggs")
                Spacer(modifier = Modifier.height(8.dp))
                AddItemButton(text = "add an item") {
                    viewModel.addItem(ShoppingItem("New Item"))
                }
            }
        }
    }
}


@Composable
fun CookDoListCard(viewModel: ShoppingListViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp / 2 - 24.dp
    val cookDoItems by viewModel.cookDoItems.collectAsState()

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .width(screenWidthDp)
            .height(300.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF6F6F6)
        )
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cook_do), // replace with your cook-do icon drawable
                        contentDescription = "Cook-Do Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "today’s cook-dos", fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(cookDoItems) { item ->
                CheckboxListItem(item.name, item.isChecked) {
                    viewModel.onCookDoItemCheckedChange(item, it)
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AddItemButton(text = "add a cook-do") {
                    viewModel.addCookDoItem(ShoppingItem("New Cook-Do Item"))
                }
            }
        }
    }
}



@Composable
fun CheckboxListItem(text: String, checked: Boolean = false, onCheckedChange: (Boolean) -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked, onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Composable
fun AddItemButton(text: String, onClick: () -> Unit = {}) {
    TextButton(onClick = onClick) {
        Text(text = text)
    }
}

@Composable
fun ElevatedButtonExample(text: String, onRecipe: () -> Unit) {
    Button(
        onClick = { onRecipe() },
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .height(50.dp) // Adjust the height as needed
            .padding(4.dp)
    ) {
        Text(
            text = text, fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(4.dp)
        )
    }
}
