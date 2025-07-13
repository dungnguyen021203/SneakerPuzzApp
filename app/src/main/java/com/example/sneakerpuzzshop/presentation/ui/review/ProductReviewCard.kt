package com.example.sneakerpuzzshop.presentation.ui.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.domain.model.ReviewModel
import com.example.sneakerpuzzshop.utils.formatTimestamp

@Composable
fun ProductReviewCard(modifier: Modifier = Modifier, review: ReviewModel) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = review.avatar,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(text = review.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        Icon(Icons.Default.MoreVert, contentDescription = "More Vert")
    }

    Spacer(modifier = Modifier.height(5.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = Color.Blue,
            modifier = Modifier.size(15.dp)
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = Color.Blue,
            modifier = Modifier.size(15.dp)
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = Color.Blue,
            modifier = Modifier.size(15.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.StarHalf,
            contentDescription = "Star",
            tint = Color.Blue, modifier = Modifier.size(15.dp)
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = Color.LightGray, modifier = Modifier.size(15.dp)
        )
        Text(text = formatTimestamp(review.createdAt), fontSize = 12.sp)
    }

    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = review.content, style = MaterialTheme.typography.bodySmall,
        maxLines = if (expanded) Int.MAX_VALUE else 3,
        overflow = TextOverflow.Ellipsis
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = if (expanded) "Show less" else "Read more",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Blue,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.clickable { expanded = !expanded }
    )

    Spacer(modifier = Modifier.height(10.dp))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = formatTimestamp(review.createdAt), style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = review.response,
                style = MaterialTheme.typography.bodySmall,
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (expanded) "Show less" else "Read more",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { expanded = !expanded }
            )
        }
    }

    Spacer(modifier = Modifier.height(15.dp))
}