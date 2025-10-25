package com.jtautomation02.foodcravies.ui.features.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jtautomation02.foodcravies.R
import com.jtautomation02.foodcravies.ui.theme.BackgroundLight
import com.jtautomation02.foodcravies.ui.theme.BorderLight
import com.jtautomation02.foodcravies.ui.theme.OrangePrimary
import com.jtautomation02.foodcravies.ui.theme.TextPrimaryLight
import com.jtautomation02.foodcravies.ui.theme.TextSecondaryLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onNavigateBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    // --- REMOVED ---
    // No Scaffold, no bottomBar.

    LazyColumn(
        modifier = Modifier.fillMaxSize(), // The NavHost handles padding
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // The TopBar is now the first item in the list
            AccountTopBar(onNavigateBack = onNavigateBack)
        }
        item {
            ProfileHeaderSection()
        }
        item {
            AccountDetailsSection()
        }
        item {
            AccountManagementSection()
        }
        item {
            SettingsSection()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopBar(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                "Account",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color = TextPrimaryLight
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimaryLight,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        actions = {
            // Empty spacer to balance the title in the center
            Spacer(Modifier.width(48.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundLight
        )
    )
}
@Composable
fun ProfileHeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.placeholder_profile), // Placeholder
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp) // w-32 h-32
                    .clip(CircleShape)
            )
            IconButton(
                onClick = { /* Handle change photo */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(36.dp) // w-9 h-9
                    .background(OrangePrimary, CircleShape)
            ) {
                Icon(
                    // Use painterResource to load your SVG from res/drawable
                    painter = painterResource(id = R.drawable.ic_camera), // <-- CHANGED
                    contentDescription = "Change Photo",
                    tint = Color.White, // This will still work
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Sophia Carter",
                fontSize = 24.sp, // text-2xl
                fontWeight = FontWeight.Bold,
                color = TextPrimaryLight
            )
            Text(
                text = "Joined in 2021",
                fontSize = 14.sp, // text-sm
                color = TextSecondaryLight
            )
        }

        Button(
            onClick = { /* Handle edit profile */ },
            shape = CircleShape, // rounded-full
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePrimary.copy(alpha = 0.1f), // bg-primary/10
                contentColor = OrangePrimary
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(18.dp) // text-lg
            )
            Spacer(Modifier.width(8.dp)) // gap-2
            Text(text = "Edit Profile", fontWeight = FontWeight.SemiBold)
        }
    }
}


@Composable
fun AccountDetailsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            "Account Details",
            fontSize = 18.sp, // text-lg
            fontWeight = FontWeight.Bold,
            color = TextPrimaryLight,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoCardRow(label = "Name", value = "Sophia Carter")
            InfoCardRow(label = "Email", value = "sophia.carter@email.com")
            InfoCardRow(label = "Phone Number", value = "+1 (555) 123-4567")
        }
    }
}

@Composable
fun AccountManagementSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            "Account Management",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryLight,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            NavCardRow(
                text = "Past Orders",
                icon = Icons.Outlined.AccountBox
            )
            NavCardRow(
                text = "Payment Methods",
                icon = Icons.Outlined.ShoppingCart
            )
        }
    }
}

@Composable
fun SettingsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            "Settings",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryLight,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            NavCardRow(text = "Saved Addresses")
            NavCardRow(text = "Notifications")
            NavCardRow(text = "App Settings")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCardRow(label: String, value: String) {
    Card(
        onClick = { /* Handle click */ },
        shape = RoundedCornerShape(12.dp), // rounded-lg
        colors = CardDefaults.cardColors(containerColor = BackgroundLight),
        border = BorderStroke(1.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 12.sp, // text-xs
                    color = TextSecondaryLight
                )
                Text(
                    text = value,
                    fontSize = 16.sp, // text-base
                    fontWeight = FontWeight.Medium,
                    color = TextPrimaryLight
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = TextSecondaryLight
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavCardRow(text: String, icon: ImageVector? = null) {
    Card(
        onClick = { /* Handle click */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundLight),
        border = BorderStroke(1.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp) // gap-4
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = TextPrimaryLight.copy(alpha = 0.8f)
                    )
                }
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimaryLight
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = TextSecondaryLight
            )
        }
    }
}