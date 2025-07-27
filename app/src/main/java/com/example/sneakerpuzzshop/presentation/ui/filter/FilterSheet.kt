package com.example.sneakerpuzzshop.presentation.ui.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSheet(
    sizes: List<String>,
    selectedSizes: Set<String>,
    onToggleSize: (String) -> Unit,
    priceRange: ClosedFloatingPointRange<Float>,
    maxPrice: Float,
    onPriceChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onApply: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Product Filter", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Size", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            sizes.forEach { size ->
                FilterChip(
                    selected = selectedSizes.contains(size),
                    onClick = { onToggleSize(size) },
                    label = { Text(text = size) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Price: ${priceRange.start.roundToInt()}đ - ${priceRange.endInclusive.roundToInt()}đ",
            style = MaterialTheme.typography.bodyLarge)
        RangeSlider(
            value = priceRange,
            onValueChange = onPriceChange,
            valueRange = 0f..maxPrice,
            steps = 4,
            modifier = Modifier.padding(horizontal = 8.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onApply,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Apply")
        }
    }
}