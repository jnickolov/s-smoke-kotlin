package eu.efficientsoft.lpl.ssmoke.mobileapp.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import eu.efficientsoft.lpl.ssmoke.mobileapp.R

@Composable
fun PasswordField (modifier: Modifier, password: String, onChange: (String) -> Unit) {
    var showPassword by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = password, onValueChange = { onChange(it) },
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword },
// OK                        content={Image(imageVector = Icons.Default.Check, contentDescription = "")}
                content = {
                    if (showPassword) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = "",
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_visibility_24),
                            contentDescription = "",
                        )
                    }
                }
            )
        }
    )
}