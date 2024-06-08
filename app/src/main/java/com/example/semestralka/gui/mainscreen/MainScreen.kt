import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.semestralka.R
import com.example.semestralka.navigation.NavigationDestination

object MainDestination : NavigationDestination {
    override val route = "main"
}

@Composable
fun MainScreen(onPrevious: () -> Unit, onNext: () -> Unit) {
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
            Text(
                text = "Here’s to a delicious meal",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "today’s meal choice",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MealCard()
            Spacer(modifier = Modifier.height(16.dp))
            ListsRow()
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun MealCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.food), // Replace with your image resource
                contentDescription = "Homemade Lasagna",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Homemade Lasagna", fontSize = 24.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ListsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ShoppingListCard()
        CookDoListCard()
    }
}

@Composable
fun ShoppingListCard() {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp / 2 - 24.dp

    val items = listOf(
        "90g prosciutto", "125g ball mozzarella", "750g minced beef", "200ml white sauce", "eggs"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(4.dp)
            .width(screenWidthDp)
            .height(293.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                Text(
                    text = "shopping list", fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "today’s meal:")
            }
            items(items) { item ->
                CheckboxListItem(item)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "other:")
                CheckboxListItem("eggs")
                Spacer(modifier = Modifier.height(8.dp))
                AddItemButton(text = "add an item")
            }
        }
    }
}

@Composable
fun CookDoListCard() {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp / 2 - 24.dp

    val cookDoItems = listOf(
        "prep the veggies",
        "restock spices",
        "meal prep for the weekend",
        "harvest some tomatoes",
        "harvest some tomatoes",
        "harvest some tomatoes",
        "harvest some tomatoes"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .width(screenWidthDp)
            .height(300.dp)
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                Text(
                    text = "today’s cook-dos", fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(cookDoItems) { item ->
                CheckboxListItem(item, checked = item == "harvest some tomatoes")
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AddItemButton(text = "add a cook-do")
            }
        }
    }
}

@Composable
fun CheckboxListItem(text: String, checked: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked, onCheckedChange = null // This can be implemented as per requirement
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Composable
fun AddItemButton(text: String) {
    TextButton(onClick = { /* Handle add item action */ }) {
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


