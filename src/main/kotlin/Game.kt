import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class Game {

    var formattedTime by mutableStateOf("00:00")
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false
    private var timeMillis = 0L
    private var lastTimestamp = 0L

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
    }


    private fun formatTime(timeMillis: Long): String{
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


    // TODO: complete submit fun
    fun submit(submittedAnswer: String){
        answer = submittedAnswer

        if (true){
            question = "3+3"
        }
        else{

        }


    }


}