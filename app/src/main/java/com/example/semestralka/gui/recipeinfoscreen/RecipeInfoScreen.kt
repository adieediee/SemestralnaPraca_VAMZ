package com.example.semestralka.gui

import SharedViewModelMealCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import com.example.semestralka.SharedViewModel
import com.example.semestralka.database.NoteItem
import com.example.semestralka.database.NoteType
import com.example.semestralka.database.Recipe
import com.example.semestralka.gui.notesscreen.ShoppingListViewModel
import com.example.semestralka.navigation.NavigationDestination

object RecipeInfoDestination : NavigationDestination {
    override val route = "recipeInfo"
}
/**
 * Obrazovka s detailmi receptu.
 *
 * @param onBack Lambda funkcia na spracovanie kliknutia na tlačidlo späť.
 * @param onEdit Lambda funkcia na spracovanie kliknutia na tlačidlo upraviť.
 * @param onDelete Lambda funkcia na spracovanie kliknutia na tlačidlo odstrániť.
 * @param sharedViewModel Zdieľaný ViewModel pre recepty.
 * @param sharedViewModelMealCard Zdieľaný ViewModel pre kartu jedla.
 * @param shoppingListViewModel ViewModel pre zoznam nákupov.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeInfoScreen(
    onBack: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
    sharedViewModelMealCard: SharedViewModelMealCard,
    shoppingListViewModel: ShoppingListViewModel // Add ShoppingListViewModel as a parameter
) {
    val recipe by sharedViewModel.selectedRecipe.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    recipe?.let { recipe ->
        if (showDialog) {
            DeleteConfirmationDialog(
                onConfirm = {
                    onDelete(recipe.id)
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }

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

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = recipe.name,
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
                        Icon(painter = painterResource(id = R.drawable.ic_time), contentDescription = stringResource(R.string.time))
                        Text(text = recipe.time)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_servings), contentDescription = stringResource(R.string.servings))
                        Text(text = recipe.servings)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_meal), contentDescription = stringResource(R.string.type))
                        Text(text = recipe.type)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                IngredientsList(recipe.ingredients) { ingredient ->

                    shoppingListViewModel.addItemWithName(ingredient)
                }
                Spacer(modifier = Modifier.height(16.dp))
                RecipeSteps(recipe.method)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { sharedViewModelMealCard.selectRecipe(recipe) }) {
                    Text(stringResource(R.string.select_for_today))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.delete_recipe))
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(R.string.edit),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = { onEdit(recipe.id) })
            )
        }
    }
}
/**
 * Dialógové okno na potvrdenie odstránenia.
 *
 * @param onConfirm Lambda funkcia na spracovanie potvrdenia odstránenia.
 * @param onDismiss Lambda funkcia na spracovanie zrušenia odstránenia.
 */
@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.delete_r)) },
        text = { Text(text = stringResource(R.string.are_you_sure_you_want_to_delete_this_recipe)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
/**
 * Zobrazenie zoznamu ingrediencií receptu.
 *
 * @param ingredients Zoznam ingrediencií vo forme reťazca.
 * @param onAddToShoppingList Lambda funkcia na pridanie ingrediencií do nákupného zoznamu.
 */
@Composable

fun IngredientsList(ingredients: String, onAddToShoppingList: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.ingredients2),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val ingredientList = ingredients.split(", ")
            ingredientList.forEach { ingredient ->
                CheckboxListItemRecipe(text = ingredient)
            }
            TextButton(onClick = {
                ingredientList.forEach { ingredient ->
                    onAddToShoppingList(ingredient)
                }
            }) {
                Text(text = stringResource(R.string.add_to_shopping_list))
            }
        }
    }
}

/**
 * Zobrazenie krokov receptu.
 *
 * @param method Kroky receptu vo forme reťazca.
 */
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
/**
 * Zobrazenie položky zoznamu s checkboxom pre recept.
 *
 * @param text Text položky.
 * @param checked Indikátor, či je položka označená. Predvolená hodnota je false.
 */
@Composable
fun CheckboxListItemRecipe(text: String, checked: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}
