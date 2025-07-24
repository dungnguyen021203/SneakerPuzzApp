package com.example.sneakerpuzzshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerpuzzshop.domain.repository.DarkModeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val darkModeRepository: DarkModeRepository
): ViewModel() {
    val dark = darkModeRepository.isDarkMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleDarkMode() {
        viewModelScope.launch {
            darkModeRepository.setDarkMode(!dark.value)
        }
    }
}