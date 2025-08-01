package eu.efficientsoft.lpl.ssmoke.mobileapp.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Composable
fun ToastMessage(
    modifier: Modifier = Modifier.padding(12.dp),
    shape: RoundedCornerShape = CircleShape
) {
    val toastMessage by ToastManager.toastMessage.collectAsState()
    val showMessage by ToastManager.showMessage.collectAsState()
    val isSuccess by ToastManager.isSuccess.collectAsState()

    if (showMessage) {
        val backgroundColor = if (isSuccess) {
            Color(0xFFB2F1C0)
        } else {
            Color(0xFFFFDAD6)
        }

        val iconColor = if (isSuccess) {
            Color(0xFF2F6A43)
        } else {
            Color(0xFFFF2514)
        }

        val icon = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Close
        val contentDescription = if (isSuccess) "Success icon" else "Error icon"

        LaunchedEffect(showMessage) {
            if (showMessage) {
                delay(30000) // Hide after 30 seconds
                ToastManager.hideToast()
            }
        }

        Row (
            modifier = modifier
                .background(backgroundColor, shape)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconColor,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = toastMessage, modifier=Modifier.clickable(
                onClick = {
                    ToastManager.hideToast()
                }
            ))
        }
    }
}

object ToastManager {
    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage

    private val _showMessage = MutableStateFlow(false)
    val showMessage: StateFlow<Boolean> = _showMessage

    private val _isSuccess = MutableStateFlow(true)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun showToast(message: String, isSuccess: Boolean) {
        _toastMessage.update { message }
        _isSuccess.update { isSuccess }
        _showMessage.update { true }
    }

    fun hideToast() {
        _showMessage.update { false }
        _toastMessage.update { "" }
    }
}