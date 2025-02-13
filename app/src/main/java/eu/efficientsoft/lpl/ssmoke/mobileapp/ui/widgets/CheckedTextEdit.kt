package eu.efficientsoft.lpl.ssmoke.mobileapp.ui.widgets

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.R


@Composable
fun CheckedTextEdit (
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChanged: (String) -> Unit,
    hint: String? = "",
    checkValue: (String) -> HintStatus = { HintStatus.OK },
    label: String? = null,
    description: String? = null,
    trailingIcon: Int? = null,
    trailingAction: () -> Unit = {}
) {

    var trailingStatus by remember { mutableStateOf(false) }

    val hintFontSize = LocalTextStyle.current.fontSize * 0.75
    val hintStatus = remember { derivedStateOf { checkValue(text) } }

    fun handleChange(txt: String) {
        onTextChanged (txt)
    }

    @Composable fun HintText (hint: String, status: HintStatus) {
        val clr: Color = when (status) {
            HintStatus.OK -> Color(8, 128, 8)
            HintStatus.WARN -> Color(128, 120, 8)
            HintStatus.ERROR -> Color(128, 0, 0)
        }
        Text(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp), text = hint, fontSize = hintFontSize, color = clr) //, style = TextStyle.Default.copy(fontSize = hintFontSize))
    }

    @Composable fun MakeTrailingIcon() {
        if (trailingIcon != null) {
            IconButton (
                onClick = {
                    trailingStatus = ! trailingStatus
                    trailingAction()
                }
            ) {
                Image(
                    painter = painterResource(id = trailingIcon),
                    contentDescription = "",
                )
            }
        }
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = text,
            label = { Text(label ?: "") },
            onValueChange = { handleChange(it) },
            supportingText = { if (! hint.isNullOrEmpty()) HintText(hint, hintStatus.value) },
            trailingIcon = { if (trailingIcon != null) MakeTrailingIcon() }
        )

        if (! description.isNullOrEmpty()) {
           // Spacer(Modifier.height(8.dp))
            Text (text = description, fontSize = hintFontSize, modifier = Modifier.padding(start=16.dp, top = 4.dp))
//            when (hintStatus) {
//                HintStatus.OK -> Text(text = hint, fontSize = hintFontSize, color = Color(8, 120, 8))
//                HintStatus.WARN -> Text(text = hint, fontSize = hintFontSize, color = Color.Yellow)
//                HintStatus.ERROR -> Text(modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp), text = hint, fontSize = hintFontSize, color = Color.Red, style = TextStyle.Default.copy(fontSize = hintFontSize))
//            }
        }


    }
}

enum class HintStatus { OK, WARN, ERROR }

