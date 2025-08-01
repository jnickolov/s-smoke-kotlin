package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import eu.efficientsoft.lpl.ssmoke.mobileapp.R
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.LPLBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SSmokeTopBar(
    title: String,
    onMenuClick: () -> Unit,
    onActionClick: () -> Unit) {

    val ssmokeBlue = LPLBlue; //Color(0x78, 0x96, 0xcc)
    val ssmokeColors  = TopAppBarDefaults.topAppBarColors(
        containerColor = ssmokeBlue,
        navigationIconContentColor = Color.White,
        actionIconContentColor = Color.White,
        titleContentColor = Color.White,
    )

    TopAppBar(
        title = { Text(fontWeight = FontWeight.Bold, text = "  $title") },
        colors = ssmokeColors,
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {  //  help button
            IconButton(onClick = onActionClick) {
                Image(painter = painterResource(id = R.drawable.baseline_help_outline_24),"Help")
            }
        }
    )

}
