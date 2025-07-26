package com.example.sneakerpuzzshop.presentation.ui.search

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
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
    val focusManager = LocalFocusManager.current

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
            shape = RoundedCornerShape(30.dp),
            trailingIcon = {
                if (query.isNotBlank()) {
                    IconButton(onClick = {
                        productViewModel.onQueryChange("")
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    productViewModel.performSearch(query = query)
                    navController.navigate(ROUTE_SEARCH_RESULT + "${Uri.encode(query)}")
                }
            )
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
                            Modifier.fillMaxWidth().clickable {
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
