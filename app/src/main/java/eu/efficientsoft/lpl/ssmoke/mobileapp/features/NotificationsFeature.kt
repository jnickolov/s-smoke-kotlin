package eu.efficientsoft.lpl.ssmoke.mobileapp.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp

@Composable fun NotificationsScreen () {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            fontSize = LocalTextStyle.current.fontSize * 1.75,
            text = "Получаване на съобщения на това устройство"
        )

        //var checked by remember { mutableStateOf(true) }
        var checked1 by remember { mutableStateOf(false)}
        var checked2 by remember { mutableStateOf(false) }
        var checked3 by remember { mutableStateOf(false) }

        Row (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = "Регистриране на устройството")

            Switch(
                modifier = Modifier.semantics { contentDescription = "Demo with icon" },
                checked = checked1,
                onCheckedChange = { checked1 = it },
                thumbContent = {
                    if (checked1) {
                        // Icon isn't focusable, no need for content description
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                }
            )
        }

        Divider(thickness = 1.dp, color = Color.LightGray)

        /////////////////////////////////
        Row (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = "Получаване на системни съобщения")

            Switch(
                modifier = Modifier.semantics { contentDescription = "Demo with icon" },
                checked = checked2,
                onCheckedChange = { checked2 = it },
                thumbContent = {
                    if (checked2) {
                        // Icon isn't focusable, no need for content description
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                }
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = "Получаване на съобщения за аларми")

            Switch(
                modifier = Modifier.semantics { contentDescription = "Demo with icon" },
                checked = checked3,
                onCheckedChange = { checked3 = it },
                thumbContent = {
                    if (checked3) {
                        // Icon isn't focusable, no need for content description
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                }
            )
        }
    }
}
