package com.example.sneakerpuzzshop.presentation.ui.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.EditProfileItemRow
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.utils.others.bitmapToFile
import com.example.sneakerpuzzshop.utils.others.uriToFile
import com.example.sneakerpuzzshop.utils.ui.ROUTE_LOGIN
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    // Variables
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }


    // State Collector
    val authState by viewModel.userInformation.collectAsState()
    val avatarUploadState by viewModel.uploadAvatarFlow.collectAsState()
    val deleteState by viewModel.deleteAccount.collectAsState()

    // Get information
    LaunchedEffect(Unit) {
        viewModel.getUserInformation()
    }
    var user = (authState as? Resource.Success)?.data
    val currentUserUid = viewModel.currentUser?.uid
    LaunchedEffect(avatarUploadState) {
        when (avatarUploadState) {
            is Resource.Success -> {
                val newAvatarUrl = (avatarUploadState as Resource.Success).data
                viewModel.updateUserAvatarUrl(newAvatarUrl)
                showToast(context, "Ảnh đã được cập nhật!")
            }

            is Resource.Failure -> {
                showToast(context, "Lỗi upload ảnh")
            }

            else -> {}
        }
    }
    LaunchedEffect(deleteState) {
        when (deleteState) {
            is Resource.Success -> {
                showToast(context, "Xoá tài khoản thành công!")
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(0) { inclusive = true }
                }
            }

            is Resource.Failure -> {
                showToast(context, "Lỗi khi xoá tài khoản.")
            }

            else -> {}
        }
    }


    // Take picture from camera and Gallery
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { takenBitmap ->
        takenBitmap?.let {
            bitmap = it
            val file = bitmapToFile(context, it)
            viewModel.uploadAvatar(file)
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

            val file = uriToFile(context, it)
            viewModel.uploadAvatar(file)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edit Profile", fontWeight = FontWeight.Bold)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Card(
                shape = RoundedCornerShape(100.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                if (user?.avatar?.isBlank() == true) {
                    Image(
                        painterResource(R.drawable.pro5),
                        contentDescription = "Profile image",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    if (avatarUploadState == Resource.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(user?.avatar),
                            contentDescription = "Profile image",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Text(
                text = "Change Profile Picture",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.clickable {
                    showSheet = true
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            HorizontalDivider()

            Text(
                text = "Profile Information",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            EditProfileItemRow("Name", user?.name.toString(), true, navController)

            EditProfileItemRow(
                "Username",
                viewModel.currentUser?.email.toString(),
                false,
                navController
            )

            EditProfileItemRow("Password", "*********", true, navController)

            HorizontalDivider()

            Text(
                text = "Personal Information",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            EditProfileItemRow(
                "UserID",
                viewModel.currentUser?.uid.toString(),
                false,
                navController
            )

            EditProfileItemRow("E-mail", user?.email.toString(), false, navController)

            EditProfileItemRow("Phone", user?.phoneNumber.toString(), true, navController)

            EditProfileItemRow("Address", user?.address.toString(), true, navController)

            HorizontalDivider()

            Spacer(modifier = Modifier.height(5.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Delete Account",
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        showDeleteDialog = true
                    },
                    color = Color.Red
                )
            }

            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Choose Image Source",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    sheetState.hide()
                                }
                                launcher.launch() // gọi camera
                                showSheet = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Take Photo")
                        }

                        Button(
                            onClick = {
                                coroutineScope.launch { sheetState.hide() }
                                launchImage.launch("image/*") // gọi thư viện
                                showSheet = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Choose from Gallery")
                        }
                    }
                }
            }
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Xác nhận xoá tài khoản") },
                    text = { Text("Bạn có chắc chắn muốn xoá tài khoản này? Hành động này không thể hoàn tác.") },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.deleteAccount(uid = currentUserUid.toString())
                            showDeleteDialog = false
                        }) {
                            Text("Xoá")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Huỷ")
                        }
                    }
                )
            }

        }
    }
}