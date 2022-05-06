import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class Game(val resultsManager: ResultsManager) {
    var formattedTime by mutableStateOf("00:00")
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    var isActive = false
    private var timeMillis = 0L
    private var lastTimestamp = 0L
    private var points = 0

    var question by mutableStateOf("2+2")
    var answer by mutableStateOf("2+2")

    fun start(){
        if(isActive) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            this@Game.isActive = true
            while(this@Game.isActive){
                delay(1000L)
                val currentTime = System.currentTimeMillis()
                timeMillis +=  currentTime - lastTimestamp
                lastTimestamp = currentTime
                formattedTime = formatTime(timeMillis)

            }
        }
    }


    fun reset(){
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00"
        isActive = false
        points = 0
    }



    // TODO: complete submit fun
    fun submit(submittedAnswer: String){
        answer = submittedAnswer

        if (true){
            points += 1
            question = "3+3"
        }
        else{
            resultsManager.addResult(Result(playerName = "", points = points, time = timeMillis))
            reset()
        }


    }

}
fun formatTime(timeMillis: Long): String{
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(timeMillis),
        ZoneId.systemDefault()
    )

    val formatter = DateTimeFormatter.ofPattern(
        "mm:ss",
        Locale.getDefault()
    )
    return localDateTime.format(formatter)
}