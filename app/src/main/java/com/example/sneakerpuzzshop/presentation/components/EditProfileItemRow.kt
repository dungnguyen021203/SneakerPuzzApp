package com.example.sneakerpuzzshop.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.utils.ui.*

@Composable
fun EditProfileItemRow(label: String, content: String, copy: Boolean, navController: NavHostController) {
    val clipboardManager = LocalClipboardManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                color = Color(0xFF6E6E6E),
                modifier = Modifier.weight(1f)
            )
            if(content.isEmpty()){
                Text(
                    text = "Update needed...",
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    modifier = Modifier.weight(2f)
                )
            } else {
                Text(
                    text = content,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(2f)
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            if(copy) Icons.AutoMirrored.Filled.KeyboardArrowRight else Icons.Default.ContentCopy,
            contentDescription = null,
            modifier = Modifier.clickable{
                if (!copy) {
                    clipboardManager.setText(AnnotatedString(content))
                } else {
                    when(label) {
                        "Name" -> navController.navigate(ROUTE_EDIT_PROFILE_FIELD_NAME)
                        "Password" -> navController.navigate(ROUTE_EDIT_PROFILE_FIELD_PASSWORD)
                        "Phone" -> navController.navigate(ROUTE_EDIT_PROFILE_FIELD_PHONE)
                        "Address" -> navController.navigate(ROUTE_EDIT_PROFILE_FIELD_ADDRESS)
                    }
                }
            }
        )
    }
}