package eu.efficientsoft.lpl.ssmoke.mobileapp.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf


@Composable
fun PasswordTextFieldWithToggle() {
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = setPassword,
        label = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.CheckCircle
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                Icon(image, contentDescription = description)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}