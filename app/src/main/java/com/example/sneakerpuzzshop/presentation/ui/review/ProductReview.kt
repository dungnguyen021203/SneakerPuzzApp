package com.example.sneakerpuzzshop.presentation.ui.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.viewmodel.ReviewViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ReviewModel
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.utils.LoadingCircle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductReview(
    modifier: Modifier = Modifier,
    productId: String,
    navController: NavHostController,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProductReviews(productId)
    }

    val context = LocalContext.current
    val state by viewModel.review.collectAsState()

    state.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> LoadingCircle()

            is Resource.Success<*> -> {
                val reviewList = (state as Resource.Success<List<ReviewModel>>).data
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
                        val averageRating = if (reviewList.isNotEmpty()) {
                            reviewList.map { it.rating }.average()
                        } else 0.0

                        ProductReviewHeadSection(
                            rating = averageRating,
                            totalReviews = reviewList.size
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(reviewList) { review ->
                                ProductReviewCard(review = review)
                            }
                        }
                    }
                }
            }
        }
    }
}