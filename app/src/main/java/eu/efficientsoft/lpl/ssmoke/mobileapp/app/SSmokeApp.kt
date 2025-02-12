import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun SSmokeApp(modifier: Modifier = Modifier) {
    Log.i("in SSmokeApp", "Column")
    val navController = rememberNavController()
}

