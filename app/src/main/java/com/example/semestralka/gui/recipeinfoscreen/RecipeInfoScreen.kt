import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.semestralka.R
import com.example.semestralka.navigation.NavigationDestination

object RecipeInfoDestination : NavigationDestination {
    override val route = "recipe_info"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeInfoScreen(onBack: () -> Unit, onEdit: () -> Unit) {
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
            Image(
                painter = painterResource(id = R.drawable.food), // Replace with your image resource
                contentDescription = "Homemade Lasagna",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Homemade Lasagna",
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
                    Text(text = "2h")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painter = painterResource(id = R.drawable.ic_servings), contentDescription = "Servings")
                    Text(text = "8 servings")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painter = painterResource(id = R.drawable.ic_meal), contentDescription = "Dinner")
                    Text(text = "dinner")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            IngredientsList()
            Spacer(modifier = Modifier.height(16.dp))
            RecipeSteps()
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


@Composable
fun IngredientsList() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "ingredients:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val ingredients = listOf(
                "750g beef mince",
                "90g prosciutto",
                "800g tomato sauce",
                "200ml beef stock",
                "125g ball mozzarella",
                "200ml white sauce",
                "300g fresh lasagne sheets"
            )
            ingredients.forEach { ingredient ->
                CheckboxListItemRecipe(text = ingredient)
            }
            TextButton(onClick = { /* Add to shopping list */ }) {
                Text(text = "add to shopping list")
            }
        }
    }
}

@Composable
fun RecipeSteps() {
    val steps = listOf(
        "To make the meat sauce, heat 2 tbsp olive oil in a frying pan and cook 750g lean beef mince in two batches for about 10 mins until browned all over.",
        "Finely chop 4 slices of prosciutto from a 90g pack, then stir through the meat mixture.",
        "Pour over 800g passata or half our basic tomato sauce recipe and 200ml hot beef stock. Add a little grated nutmeg, then season.",
        "Bring up to the boil, then simmer for 30 mins until the sauce looks rich.",
        "Heat the oven to 180C/160C fan/gas 4 and lightly oil an ovenproof dish (about 30 x 20cm).",
        "Spoon one third of the meat sauce into the dish, then cover with some fresh lasagne sheets from a 300g pack. Drizzle over roughly 130g ready-made or homemade white sauce."
    )
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


