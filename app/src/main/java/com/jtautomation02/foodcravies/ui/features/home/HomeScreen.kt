package com.jtautomation02.foodcravies.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.tv.material3.Border
import com.jtautomation02.foodcravies.R
import com.jtautomation02.foodcravies.ui.theme.BackgroundLight
import com.jtautomation02.foodcravies.ui.theme.CardBackgroundLight
import com.jtautomation02.foodcravies.ui.theme.OrangePrimary
import com.jtautomation02.foodcravies.ui.theme.TextPrimaryLight
import com.jtautomation02.foodcravies.ui.theme.TextSecondaryLight



// Data classes to model the UI state
data class Category(val name: String, val imageRes: Int)
data class Restaurant(
    val name: String,
    val imageRes: Int,
    val rating: Double,
    val deliveryTime: String,
    val deliveryFee: String
)


// --- Sample Data ---
val sampleCategories = listOf(
    Category("All", R.drawable.ic_all), // Replace with your actual drawables
    Category("Pizza", R.drawable.ic_pizza),
    Category("Asian", R.drawable.ic_asian),
    Category("Desserts", R.drawable.ic_desserts),
    Category("Burgers", R.drawable.ic_burger_cat)
)

val featuredRestaurants = listOf(
    Restaurant(
        "The Italian Corner",
        R.drawable.pasta,
        4.6,
        "25-35 min",
        "$2.99 Delivery"
    ),
    Restaurant(
        "Spice Route",
        R.drawable.noodles,
        4.7,
        "30-40 min",
        "$1.99 Delivery"
    )
)

val currentDeals = listOf(
    Restaurant(
        "Burger Haven",
        R.drawable.ic_burger_cat,
        4.5,
        "20-30 min",
        "Free Delivery"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp) // Add padding for content below lists
    ) {
        item{
            TopSearchBar()
        }
        item {
            CategoryTabs(categories = sampleCategories)
        }

        item {
            SectionHeader("Featured Restaurants")
        }

        items(featuredRestaurants) { restaurant ->
            RestaurantInfoCard(
                restaurant = restaurant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            SectionHeader("Current Deals")
        }

        items(currentDeals) { restaurant ->
            RestaurantInfoCard(
                restaurant = restaurant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = modifier.padding(16.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search for food or restaurants") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                    // Tint is now controlled by the 'colors' parameter below
                )
            },
            shape = RoundedCornerShape(8.dp), // rounded-lg

            // --- THIS IS THE FIX ---
            // Using the explicit parameters from the signature you provided
            colors = TextFieldDefaults.colors(
                // Set container color (bg-white)
                focusedContainerColor = CardBackgroundLight,
                unfocusedContainerColor = CardBackgroundLight,
                disabledContainerColor = CardBackgroundLight,

                // Set text color (text-slate-800)
                focusedTextColor = TextPrimaryLight,
                unfocusedTextColor = TextPrimaryLight,

                // Set placeholder color (placeholder-slate-500)
                focusedPlaceholderColor = TextSecondaryLight,
                unfocusedPlaceholderColor = TextSecondaryLight,

                // Set icon color (text-slate-500)
                focusedLeadingIconColor = TextSecondaryLight,
                unfocusedLeadingIconColor = TextSecondaryLight,

                // Remove the indicator line (border-none)
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun CategoryTabs(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }

    // --- THIS IS THE CHANGE ---
    // Swapped LazyRow for a standard Row that fills the width.
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        categories.forEach { category ->
            // Pass a weight(1f) modifier to each item.
            // This makes each item take up an equal amount of space.
            CategoryTabItem(
                modifier = Modifier.weight(1f), // <-- This is the key change
                category = category,
                isSelected = category.name == selectedCategory,
                onClick = { selectedCategory = category.name }
            )
        }
    }
}

@Composable
fun CategoryTabItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // <-- Added modifier parameter
) {
    val textColor = if (isSelected) TextPrimaryLight else TextSecondaryLight
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
    val iconTint = if (isSelected) OrangePrimary else Color.Black

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        // --- THIS IS THE CHANGE ---
        // The modifier (containing .weight(1f)) is applied here.
        modifier = modifier
            .padding(top = 4.dp)
            .clickable(onClick = onClick)
            .padding(bottom = 10.dp)
            .drawBehind {
                if (isSelected) {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = OrangePrimary,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Square
                    )
                }
            }
    ) {
        Image(
            painter = painterResource(id = category.imageRes),
            contentDescription = category.name,
            modifier = Modifier
                .size(50.dp) // Size remains 50.dp as requested
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(iconTint)
        )
        Spacer(Modifier.height(8.dp)) // gap-2
        Text(
            text = category.name,
            color = textColor,
            fontWeight = fontWeight,
            fontSize = 14.sp,
            maxLines = 1 // Good practice to prevent text wrapping in this layout
        )
    }
}

@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontSize = 22.sp, // text-2xl
        fontWeight = FontWeight.Bold,
        color = TextPrimaryLight,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun RestaurantInfoCard(
    restaurant: Restaurant,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // rounded-xl
        colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = restaurant.imageRes),
                contentDescription = restaurant.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp), // h-40
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = restaurant.name,
                    fontSize = 20.sp, // text-xl
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${restaurant.rating} · ${restaurant.deliveryTime} · ${restaurant.deliveryFee}",
                    fontSize = 14.sp,
                    color = TextSecondaryLight // text-slate-600
                )
            }
        }
    }
}


// --- Previews ---

//@Preview(showBackground = true, widthDp = 360, heightDp = 800)
//@Composable
//    // Wrap in a theme if you have one set up
//    // FoodAppTheme {
//    HomeScreen(navController: NavController)
//    // }
//}
