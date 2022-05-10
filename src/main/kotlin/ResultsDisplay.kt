import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import org.jetbrains.skia.impl.Log

@Composable
fun ResultsDisplay(
    results: SnapshotStateList<Result>,
    sortResults: (Int, Boolean) -> Unit,
    playerText: MutableState<String>,
    pointsText: MutableState<String>,
    filterResults: (String) -> Unit
){
    var filterRemember by remember { mutableStateOf("") }
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)
        ) {
        item {
            Row(modifier = Modifier.fillMaxWidth()){
                OutlinedTextField(
                    textStyle = TextStyle(color = Color.Blue, fontSize = 40.sp, textAlign = TextAlign.Center),
                    singleLine = true,
                    value = filterRemember,
                    onValueChange = {
                        filterRemember = it
                        filterResults(it)
                    },
                    modifier = Modifier.width(180.dp),
                    label = { Text("Filter Player Name") }
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.LightGray)
            ) {
                ClickableText(
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(.4f)
                        .padding(8.dp),
                    text = AnnotatedString(
                        playerText.value,
                        SpanStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    ),
                    onClick = {
                        sortResults(0, true)
                    }
                )
                ClickableText(
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(.3f)
                        .padding(8.dp),
                    text = AnnotatedString(
                        pointsText.value,
                        SpanStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    ),
                    onClick = {
                        sortResults(1, true)
                    }
                )
                Text(
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(.3f)
                        .padding(8.dp),
                    text = "Time",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
        items(results) { result ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = result.playerName,
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
                        text = formatTime(result.time),
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