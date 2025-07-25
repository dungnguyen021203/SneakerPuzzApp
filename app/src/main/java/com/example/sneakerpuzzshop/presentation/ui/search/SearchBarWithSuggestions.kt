package com.example.sneakerpuzzshop.presentation.ui.search

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.example.sneakerpuzzshop.utils.ui.ROUTE_SEARCH_RESULT

@Composable
fun SearchBarWithSuggestions(
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel = hiltViewModel(),
    navController: NavHostController,
    autoFocus: Boolean = false
) {
    val query by productViewModel.query.collectAsState()
    val suggestions by productViewModel.suggestions.collectAsState()

    val focusRequester = remember { FocusRequester() }
    if (autoFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = query,
            onValueChange = productViewModel::onQueryChange, // { newValue -> productViewModel.onQueryChange(newValue) }
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                Text(text = "Search ...")
            },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (query.isNotBlank()) {
                    IconButton(onClick = {
                        productViewModel.onQueryChange("")
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        if (suggestions.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    suggestions.forEach { suggestion ->
                        Text(
                            text = suggestion,
                            Modifier.clickable {
                                productViewModel.performSearch(suggestion)
                                navController.navigate(ROUTE_SEARCH_RESULT + "${Uri.encode(suggestion)}")
                            }.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}