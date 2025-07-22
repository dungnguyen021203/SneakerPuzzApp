package com.example.sneakerpuzzshop.presentation.ui.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun OrderPrev() {
    val list = listOf<String>(
        "Pending",
        "Delivered",
        "Processing",
        "Cancelled",
        "Pending",
        "Delivered",
        "Processing",
        "Cancelled"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My Orders", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        TODO()
                    }) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(list) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(30.dp),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (item == "Processing") Color.Red else Color(0xFFF0F0F0),
                            contentColor = if (item == "Processing") Color.White else Color.Black
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = item, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Order No: 19451655",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "12-5-2025", fontSize = 16.sp, color = Color.Gray)
                    }
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                )
                            ) { append(text = "Tracking number: ") }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) { append(text = "UW34567890") }
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                    )
                                ) { append(text = "Quantity: ") }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(text = "3") }
                            },
                        )
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                    )
                                ) { append(text = "Total amount: ") }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(text = "5.689.000đ") }
                            },
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .height(36.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, color = Color.Black)
                        ) {
                            Text("Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Text("Pending", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Green)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Order No: 19451655",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "12-5-2025", fontSize = 16.sp, color = Color.Gray)
                    }
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                )
                            ) { append(text = "Tracking number: ") }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) { append(text = "UW34567890") }
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                    )
                                ) { append(text = "Quantity: ") }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(text = "3") }
                            },
                        )
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                    )
                                ) { append(text = "Total amount: ") }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(text = "5.689.000đ") }
                            },
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .height(36.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, color = Color.Black)
                        ) {
                            Text("Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Text("Pending", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Green)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Order No: 19451655",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "12-5-2025", fontSize = 16.sp, color = Color.Gray)
                    }
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                )
                            ) { append(text = "Tracking number: ") }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) { append(text = "UW34567890") }
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                    )
                                ) { append(text = "Quantity: ") }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(text = "3") }
                            },
                        )
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                    )
                                ) { append(text = "Total amount: ") }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(text = "5.689.000đ") }
                            },
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .height(36.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, color = Color.Black)
                        ) {
                            Text("Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Text("Pending", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Green)
                    }
                }
            }
        }
    }
}