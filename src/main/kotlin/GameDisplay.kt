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
fun RowScope.TopText(text: String){
    Text(
        text = text,
        fontSize = 30.sp,
        color = Color.Black
    )
}

@Composable
fun RowScope.QuestionText(text: String,modifier: Modifier = Modifier){
    Text(
        text = text,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        fontSize = 40.sp,
        color = Color.Black,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}


@Composable
fun GameDisplay(
    formattedTime: String,
    onStartClick: () -> Unit,
    onResetClick: () -> Unit,
    question: String,
    onSubmitClick: (String) -> Unit,
    isActive: Boolean,
    lives: Int,
    points: Int
){
    val focusManager = LocalFocusManager.current
            var answer by remember { mutableStateOf("") }
            var isError by remember { mutableStateOf(false) }

            fun validate(text: String) {
                isError = if(text.isNotEmpty()) {
                    !((text[0].isDigit()|| text[0]=='-') &&text.substring(1).all{char -> char.isDigit()})
                }else {
                    false
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TopText("Time $formattedTime")
                TopText("Points $points")
                TopText("♥ $lives")
            }

            Spacer(Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                ) {

                QuestionText(question, Modifier.width(150.dp))
                QuestionText(" = ")

                OutlinedTextField(
                    textStyle = TextStyle(color = Color.Blue, fontSize = 40.sp, textAlign = TextAlign.Center),
                    singleLine = true,
                    value = answer,
                    isError = isError,
                    onValueChange = {
                        if (it.length > 5) {
                            focusManager.moveFocus(FocusDirection.Right)
                        } else {
                            answer = it
                            validate(answer)
                        }
                    },
                    modifier = Modifier.width(180.dp),
                    trailingIcon = {
                        if (isError)
                            Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colors.error)
                    },
                    enabled = isActive
                )
                Spacer(Modifier.width(16.dp))
                Button(onClick = { onSubmitClick(answer)
                                   answer=""
                                 },
                enabled = !isError && answer.isNotEmpty()) {
                    Text("Submit")
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick=onStartClick,
                    enabled = !isActive) {
                    Text("Start")
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick=onResetClick,
                    enabled = isActive) {
                    Text("Reset")
                }
            }

}