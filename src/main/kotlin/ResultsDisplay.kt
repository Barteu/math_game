import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.skia.FontWeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun ResultsDisplay(
    resultsManager: ResultsManager
){
    Column(
        ) {
        resultsManager.results.forEach { result: Result ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Player: ${result.playerName}  Points: ${result.points}  Time: ${formatTime(result.time)}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            Spacer(Modifier.width(16.dp))
        }
    }
    Spacer(Modifier.width(16.dp))
}