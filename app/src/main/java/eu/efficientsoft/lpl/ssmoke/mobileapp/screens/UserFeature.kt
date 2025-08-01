package eu.efficientsoft.lpl.ssmoke.mobileapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.widgets.CheckedTextEdit
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.widgets.HintStatus

@Composable fun UserProfileScreen(i18n: I18n?) {
    Surface (modifier = Modifier.fillMaxSize()){
        Text("User Profile Screen-true")
    }
}
@Composable fun NewAccountScreen(i18n: I18n?) {
    var userName by remember { mutableStateOf("gogo") }
    var pass by remember { mutableStateOf("123456") }
    var email by remember { mutableStateOf("") }
    var canCheckUsername by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Create new account",
            fontSize = LocalTextStyle.current.fontSize * 1.75,
        )
        //Spacer(modifier = Modifier.height(1.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(8.dp),

            //horizontalArrangement = Arrangement.Center,
        ) {
           CheckedTextEdit(
                text = userName,
                onTextChanged = { userName = it },
                hint = "4 to 15 chars, latin letters, digits, symbols . _ -",
                checkValue = {
                    if (userName.length in 4..8) {
                        canCheckUsername = true;
                        HintStatus.OK
                    } else {
                        canCheckUsername = false;
                        HintStatus.ERROR
                    }
                },

               label = "Enter user name",
               description = "NOTE: user name cannot be changed!",
               // raboti!!!
               //trailingIcon = R.drawable.baseline_edit_24,
               //trailingAction = { Log.i("USER FEATURE....", "Trailing action ")}
               modifier = Modifier
                   .weight(1.0f)
                   .padding(0.dp, 0.dp, 10.dp, 0.dp)
            )

            ElevatedButton(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                enabled = canCheckUsername,
               // modifier = Modifier.fillMaxHeight(),
                onClick = { /*TODO*/ }) {
                Text("Check name")
            }
        }
        //Spacer(modifier = Modifier.height(1.dp))
        CheckedTextEdit(
            modifier = Modifier
                .fillMaxWidth()
                //.align(Alignment.CenterHorizontally)
                .padding(8.dp),
            text = pass,
            onTextChanged = { pass = it },
            hint = "6 to 15 chars, avoid symbols \"+</\\>\"",
            checkValue = { if (pass.length in 6..15) HintStatus.OK else HintStatus.ERROR },
            label = "Enter user password",
            description = "Password can be changed later"
        )

        //Email
        CheckedTextEdit(
            modifier = Modifier
                .fillMaxWidth()
                //.align(Alignment.CenterHorizontally)
                .padding(8.dp),
            text = email,
            onTextChanged = { email = it },
            hint = "Enter a valid email, or leave blank",
            checkValue = {  HintStatus.OK },
            label = "Email",
            description = "Email is not necessary and can be added later"
        )

        //Preferrer language

        // Action
        Spacer(Modifier.height(8.dp))
        ElevatedButton(onClick = { /*TODO*/ }) {
            Text("Create account")
        }
    }
}


//@Preview
//@Composable
//fun NewAccountScreenPreview () {
//    NewAccountScreen()
//}