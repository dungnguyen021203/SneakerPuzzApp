package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.decode.ImageSource
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.utils.LoadingCircle

@Composable
fun ProductDetails(
    modifier: Modifier = Modifier,
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProductDetails(productId)
    }

    val context = LocalContext.current
    val state by viewModel.productDetails.collectAsState()

    state.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> LoadingCircle()
            is Resource.Success<*> -> {
                val product = (state as Resource.Success).data


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetails() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painterResource(id = R.drawable.ic_google_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Blue)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(4) {
                Image(
                    painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                )
            }
        }
        ProductDetailsBottom()
    }
}

@Preview
@Composable
fun ProductDetailsBottom(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        color = Color.LightGray,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                    Text(
                        text = "5.0 (199)",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Icon(Icons.Default.Share, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFFEB3B))
                        .padding(5.dp)
                ) {
                    Text(
                        text = "-50%",
                        style = TextStyle(fontSize = 12.sp),
                        color = Color.Black,
                    )
                }
                Text(
                    text = "$122.6 - $334.0",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Green Nike sports shoe",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Stock: In Stock", color = Color.Black, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(painterResource(id = R.drawable.ic_google_logo), contentDescription = null)
                Text(
                    text = "Nike",
                    color = Color.Black,
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Bold
                )
                Icon(Icons.Default.Done, contentDescription = null, tint = Color.Blue)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Size", style = MaterialTheme.typography.titleMedium)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    "EU 38",
                    "EU 39",
                    "EU 40",
                    "EU 41",
                    "EU 42",
                    "EU 43",
                    "EU 44"
                ).chunked(3).forEach { rowSize ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowSize.forEach { size ->
                            OutlinedButton(
                                onClick = { /* TODO */ },
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = size)
                            }
                        }
                        repeat(3 - rowSize.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Product description: ", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    "More Air, less bulk. The Dn8 takes our Dynamic Air system and condenses it into a sleek, low-profile package. " +
                            "Powered by eight pressurised Air tubes, it gives you a responsive sensation with every step." +
                            " Enter an unreal experience of movement.", fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            HorizontalDivider(color = Color.Black)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Reviews: (199)", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = { /* decrease quantity */ }) {
                        Icon(
                            Icons.Default.RemoveCircle,
                            contentDescription = "Decrease",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(text = "1", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    IconButton(onClick = { /* increase quantity */ }) {
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = "Increase",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Button(
                    onClick = { /* Add to Bag */ },
                    modifier = Modifier.height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.ShoppingBag, contentDescription = "Add to Bag")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add to Bag")
                }
            }
        }
    }

}