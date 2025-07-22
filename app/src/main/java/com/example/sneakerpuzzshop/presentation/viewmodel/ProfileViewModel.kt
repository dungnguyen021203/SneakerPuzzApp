package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.usecase.EditUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editUserNameUseCase: EditUserNameUseCase
): ViewModel() {

    private val _editUserName = MutableStateFlow<Resource<Unit>?>(null)
    val editUserName: StateFlow<Resource<Unit>?> = _editUserName.asStateFlow()

    fun updateUserName(userId: String, userName: String) = viewModelScope.launch {
        _editUserName.value = Resource.Loading
        val result = editUserNameUseCase(userId, userName)
        _editUserName.value = result
    }
}