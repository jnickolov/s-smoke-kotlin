package eu.efficientsoft.lpl.ssmoke.mobileapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.widgets.PasswordField

data class LoginRequestDAO (val username: String, val password: String)

data class LoginResponseDAO (
    val nm: String,
    val unm: String,
    val email: String?,
    val lang: String,
    val props: Map<String, String>)

@Composable
fun LoginScreen (
    i18n: I18n?,
    onLogin: (unm: String, pwd: String)->Unit,
    onNewAccount: () -> Unit) {

    var userName by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {        Text(
        text = "Please login",
        fontSize = LocalTextStyle.current.fontSize * 1.75,
    )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField (value = userName, label = { Text("User name") }, placeholder = { Text("User name") }, onValueChange = { userName = it })

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField (modifier = Modifier, password = pass, onChange = { pass = it })

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedButton (onClick = {
            onLogin (userName, pass);
        }) {
            Text(text = " Login ")
        }

        HorizontalDivider(modifier = Modifier.padding(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
//            TextButton(onClick = { onLogin() }) {
//                Text("Forgot password?")
//            }
//            TextButton(onClick = {
//                onNewAccount()
//            }) {
//                Text("Create new account")
//            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview (
//    i18n: I18n? = i18nBg_preloaded,
//    onLogin: ()->Unit = {},
//    onNewAccount: () -> Unit = {}) {
//
//
//    LoginScreen (i18n, onLogin, onNewAccount)
//}