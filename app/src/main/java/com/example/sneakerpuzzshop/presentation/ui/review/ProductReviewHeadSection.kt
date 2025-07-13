package com.example.sneakerpuzzshop.presentation.ui.review

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.domain.model.ReviewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductReviewHeadSection(
    modifier: Modifier = Modifier,
    review: ReviewModel,
    navController: NavHostController
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ProductHeadSection() {
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Reviews & Ratings", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Ratings and reviews are verified and are from people who use the same type of device that you use",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(15.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(text = "4.7", fontSize = 70.sp, color = Color.Black)
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
            Text(text = "199 reviews", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = null
                    )
                    Text(text = "John Doe", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Icon(Icons.Default.MoreVert, contentDescription = "")
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Blue,
                    modifier = Modifier.size(15.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Blue,
                    modifier = Modifier.size(15.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Blue,
                    modifier = Modifier.size(15.dp)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.StarHalf,
                    contentDescription = "",
                    tint = Color.Blue, modifier = Modifier.size(15.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.LightGray, modifier = Modifier.size(15.dp)
                )
                Text(text = "01 - Nov - 2023", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Hands down one of the best sneaker shops online! I bought a pair of Adidas Ultraboost and they arrived in perfect condition. Customer service was responsive and even helped me pick the right size.\n" +
                        "Packaging was neat and I got a small thank-you card in the box — such a nice touch!\n" +
                        "Will definitely recommend to friends.", fontSize = 14.sp
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
                            text = "SneakerPuzzShop",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "01-Nov-2023", fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Dear Minh Tran,\n" +
                                "Wow — thank you for the amazing review! We’re thrilled to know you loved your shopping experience with us.\n" +
                                "Our team works hard to provide both quality products and service, so it means a lot that you noticed the extra touches.\n" +
                                "Hope your Ultraboosts keep you moving in style, and we’ll be here whenever you need your next pair! \uD83D\uDC5F\uD83D\uDCA5",
                        maxLines = if (expanded) Int.MAX_VALUE else 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (expanded) "Show less" else "Read more",
                        fontSize = 14.sp,
                        color = Color.Blue,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressBar(number: Int, fraction: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "$number", fontSize = 14.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color.LightGray)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color.Blue)
            )
        }
    }
}