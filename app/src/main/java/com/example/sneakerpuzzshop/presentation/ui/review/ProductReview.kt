package com.example.sneakerpuzzshop.presentation.ui.review

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.viewmodel.ReviewViewModel
import androidx.compose.runtime.getValue
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ReviewModel
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.utils.LoadingCircle

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
                val review = (state as Resource.Success<List<ReviewModel>>).data


            }
        }
    }
}