import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)
        ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.LightGray)
            ) {
                Text(
                    text = "Player",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(.4f)
                        .padding(8.dp)
                )
                Text(
                    text = "Points",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(.3f)
                        .padding(8.dp)
                )
                Text(
                    text = "Time",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(.3f)
                        .padding(8.dp)
                )
            }
        }
        items(resultsManager.results.toTypedArray()) { result ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${result.playerName}",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .weight(.4f)
                            .padding(8.dp)
                    )
                    Text(
                        text = "${result.points}",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .weight(.3f)
                            .padding(8.dp)
                    )
                    Text(
                        text = "${formatTime(result.time)}",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .weight(.3f)
                            .padding(8.dp)
                    )
                }
        }
    }
}