package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class SSmokeAppStatusViewModel: ViewModel() {
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage = _errorMessage
}