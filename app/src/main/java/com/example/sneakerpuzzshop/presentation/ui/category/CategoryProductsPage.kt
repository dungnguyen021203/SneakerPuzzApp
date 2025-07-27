package com.example.sneakerpuzzshop.presentation.ui.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.presentation.components.ProductCard
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.ui.filter.FilterSheet
import com.example.sneakerpuzzshop.presentation.viewmodel.HomeViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryProductsPage(
    modifier: Modifier = Modifier,
    categoryId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(categoryId) {
        viewModel.loadProductsByCategory(categoryId)
    }

    val context = LocalContext.current
    val state by viewModel.product.collectAsState()
    val homeState by homeViewModel.categories.collectAsState()

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var selectedSizes by remember { mutableStateOf(setOf<String>()) }
    var selectedPriceRange by remember { mutableStateOf(0f..0f) }


    state.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> {
                LoadingCircle()
            }

            is Resource.Success<*> -> {
                val productList = (state as Resource.Success<List<ProductModel>>).data
                val categoryId = productList.firstOrNull()?.category
                val categoryName = homeState.find { it.id == categoryId }?.name ?: "Category"

                val priceList = productList.mapNotNull { it.actualPrice.toFloatOrNull() }
                val maxPriceVal = priceList.maxOrNull() ?: 0f

                LaunchedEffect(maxPriceVal) {
                    selectedPriceRange = 0f..maxPriceVal
                }

                val filtered = remember(productList, selectedSizes, selectedPriceRange) {
                    productList.filter { p ->
                        val price = p.actualPrice.toFloatOrNull() ?: 0f
                        val matchSize = selectedSizes.isEmpty() ||
                                selectedSizes.any { size -> p.sizes.containsKey(size) }
                        val matchPrice = price in selectedPriceRange
                        matchSize && matchPrice
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = categoryName, fontWeight = FontWeight.Bold)
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
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
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 8.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(filtered.chunked(2)) { row ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    row.forEach { product ->
                                        val category =
                                            homeState.find { it.id == product.category }
                                        ProductCard(
                                            product = product,
                                            categoryImage = category?.imageUrl,
                                            modifier = Modifier.weight(1f),
                                            navController = navController
                                        )
                                    }
                                    if (row.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }

                        // Filter button
                        FloatingActionButton(
                            onClick = { scope.launch { sheetState.show() } },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Default.FilterList, contentDescription = "Lọc")
                        }
                    }
                }

                if (sheetState.isVisible) {
                    ModalBottomSheet(
                        onDismissRequest = { scope.launch { sheetState.hide() } },
                        sheetState = sheetState,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ) {
                        val sizeInCategory = productList.flatMap { it.sizes.keys }.distinct()
                            .sortedBy { it.toFloatOrNull() ?: 0f }
                        FilterSheet(
                            sizes = sizeInCategory,
                            selectedSizes = selectedSizes,
                            onToggleSize = { size ->
                                selectedSizes =
                                    if (selectedSizes.contains(size)) selectedSizes - size
                                    else selectedSizes + size
                            },
                            priceRange = selectedPriceRange,
                            maxPrice = maxPriceVal,
                            onPriceChange = { selectedPriceRange = it },
                            onApply = {
                                scope.launch { sheetState.hide() }
                            }
                        )
                    }
                }
            }
        }
    }
}