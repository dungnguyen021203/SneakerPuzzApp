package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.usecase.EditAddressUseCase
import com.example.sneakerpuzzshop.domain.usecase.EditPhoneUseCase
import com.example.sneakerpuzzshop.domain.usecase.EditUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editUserNameUseCase: EditUserNameUseCase,
    private val editPhoneUseCase: EditPhoneUseCase,
    private val editAddressUseCase: EditAddressUseCase
): ViewModel() {

    private val _editUserName = MutableStateFlow<Resource<Unit>?>(null)
    val editUserName: StateFlow<Resource<Unit>?> = _editUserName.asStateFlow()

    private val _editUserPhone = MutableStateFlow<Resource<Unit>?>(null)
    val editUserPhone: StateFlow<Resource<Unit>?> = _editUserPhone.asStateFlow()

    private val _editUserAddress = MutableStateFlow<Resource<Unit>?>(null)
    val editUserAddress: StateFlow<Resource<Unit>?> = _editUserAddress.asStateFlow()

    fun updateUserName(userId: String, userName: String) = viewModelScope.launch {
        _editUserName.value = Resource.Loading
        val result = editUserNameUseCase(userId, userName)
        _editUserName.value = result
    }

    fun updateUserPhone(userId: String, userPhone: String) = viewModelScope.launch {
        _editUserPhone.value = Resource.Loading
        val result = editPhoneUseCase(userId, userPhone)
        _editUserPhone.value = result
    }

    fun updateUserAddress(userId: String, userAddress: String) = viewModelScope.launch {
        _editUserAddress.value = Resource.Loading
        val result = editAddressUseCase(userId, userAddress)
        _editUserAddress.value = result
    }
}