package com.example.semestralka.gui

import ViewModelFactory
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.semestralka.R
import com.example.semestralka.navigation.NavigationDestination

object AddRecipeDestination : NavigationDestination {
    override val route = "add_recipes"
    const val recipeIdArg = "recipeId"
}

/**
 * Obrazovka na pridanie alebo úpravu receptu.
 * Časti kódu sú prevzaté z
 *  https://developer.android.com/develop/ui/compose/side-effects#launchedeffect
 *  https://stackoverflow.com/questions/60608101/how-request-permissions-with-jetpack-compose
 * @param navController Navigačný ovládač pre navigáciu medzi obrazovkami.
 * @param recipeId ID receptu, ak ide o úpravu existujúceho receptu, inak null.
 * @param viewModel ViewModel pre pridanie alebo úpravu receptu.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    recipeId: Int? = null,
    viewModel: AddRecipeViewModel = viewModel(factory = ViewModelFactory)
) {
    val context = LocalContext.current

    LaunchedEffect(recipeId) {
        recipeId?.let {
            viewModel.loadRecipe(it)
        }
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageUri = it
            viewModel.imageUri = it.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (recipeId != null) stringResource(R.string.edit_recipe) else stringResource(
                    R.string.add_recipe
                )
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.time,
                onValueChange = { viewModel.time = it },
                label = { Text(stringResource(R.string.time)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.servings,
                onValueChange = { viewModel.servings = it },
                label = { Text(stringResource(R.string.servings)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.ingredients,
                onValueChange = { viewModel.ingredients = it },
                label = { Text(stringResource(R.string.ingredients)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.type,
                onValueChange = { viewModel.type = it },
                label = { Text(stringResource(R.string.type)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.method,
                onValueChange = { viewModel.method = it },
                label = { Text(stringResource(R.string.method)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { launcher.launch(arrayOf("image/*")) }) {
                Text(stringResource(R.string.pick_image))
            }
            Spacer(modifier = Modifier.height(16.dp))
            imageUri?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.saveRecipe(recipeId) {
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save_recipe))
            }
        }
    }
}
