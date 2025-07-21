package com.example.sneakerpuzzshop.presentation.ui.pages

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.ui.review.ProductReviewCard
import com.example.sneakerpuzzshop.presentation.ui.review.ProductReviewHeadSection
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.utils.ui.ROUTE_HOME
import com.example.sneakerpuzzshop.utils.ui.ROUTE_LOGIN
import com.google.firebase.Firebase
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Something")
        TextButton(
            onClick = {
                viewModel.signOut()
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign out", fontSize = 18.sp)
        }
    }
}

// Take Image from camera or gallery
@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun ProfilePRe() {

    var showDialog by remember { mutableStateOf(false) }

    // upload to fb storage
    var isUploading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var bitmap: Bitmap? by remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        if (it != null) {
            bitmap = it
        }
    }

    val launchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profile", fontWeight = FontWeight.Bold)
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
            Box {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap?.asImageBitmap()!!,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .size(200.dp)
                    )
                } else {
                    Image(
                        painterResource(id =R.drawable.ic_google_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .size(200.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 160.dp, start = 200.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.outline_photo_camera_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .clickable { showDialog = true }
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
                Button(onClick = {
//                    isUploading = true
//                    bitmap.value.let { bitmap ->
//                        uploadToFirebase(bitmap, context as ComponentActivity) { success ->
//                            isUploading = false
//                            if (success) {
//                                showToast(context, "Success")
//                            } else {
//                                showToast(context, "failed")
//                            }
//                        }
//                    }
                }) {
                    Text(text = "Upload Image", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Alert Dialog
            if (showDialog == true) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = 10.dp
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Red)
                    ) {
                        Column(modifier = Modifier.padding(start = 60.dp)) {
                            Image(
                                painter = painterResource(R.drawable.outline_photo_camera_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        launcher.launch()
                                        showDialog = false
                                    }
                            )
                            Text(text = "Camera", color = Color.White)
                        }

                        Column(modifier = Modifier.padding(start = 60.dp)) {
                            Image(
                                painter = painterResource(R.drawable.outline_photo_camera_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        launchImage.launch("image/*")
                                        showDialog = false
                                    }
                            )
                            Text(text = "Gallery", color = Color.White)
                        }

                        Column(modifier = Modifier.padding(start = 50.dp, bottom = 80.dp)) {
                            Text(text = "X", color = Color.Black, modifier = Modifier.clickable {
                                showDialog = false
                            })
                        }
                    }
                }
            }
        }
    }
    //private fun uploadToFirebase(
//    bitmap: Bitmap,
//    context: ComponentActivity,
//    callback: (Boolean) -> Unit
//) {
//    val storageRef = Firebase.storage.reference
//    val imageRef = storageRef.child("images/{$bitmap")
//
//    val baos = ByteArrayOutputStream()
//    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//
//    val imageData = baos.toByteArray()
//    imageRef.putBytes(imageData).addon {
//
//    }
//}
}

// Take Image from camera or gallery
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ProfilePRe2() {

}