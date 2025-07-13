package com.example.sneakerpuzzshop.presentation.ui.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.presentation.components.ProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductReviewHeadSection(
    rating: Double,
    totalReviews: Int
) {
    Text(
        text = stringResource(id = R.string.review),
        style = MaterialTheme.typography.bodySmall
    )

    Spacer(modifier = Modifier.height(15.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(text = "$rating", fontSize = 70.sp, color = Color.Black)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressBar(5, 0.7f)
            ProgressBar(4, 0.5f)
            ProgressBar(3, 0.3f)
            ProgressBar(2, 0.2f)
            ProgressBar(1, 0.1f)
        }
    }

    Row {
        Icon(imageVector = Icons.Default.Star, contentDescription = "", tint = Color.Blue)
        Icon(imageVector = Icons.Default.Star, contentDescription = "", tint = Color.Blue)
        Icon(imageVector = Icons.Default.Star, contentDescription = "", tint = Color.Blue)
        Icon(
            imageVector = Icons.AutoMirrored.Filled.StarHalf,
            contentDescription = "",
            tint = Color.Blue
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "",
            tint = Color.LightGray
        )
    }

    Spacer(modifier = Modifier.height(5.dp))

    Text(text = "$totalReviews reviews", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
}
