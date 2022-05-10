import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class Game() {
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    var isActive = false
    private var timeMillis = 0L
    private var lastTimestamp = 0L

    var formattedTime by mutableStateOf("00:00")
    var points by mutableStateOf(0)
    var question by mutableStateOf("❔❔❔")
    var answer by mutableStateOf("")
    var lives by mutableStateOf(3)

    private var answerChecker: (String) -> Boolean = {_: String -> false }

    fun loadNewQuestion(){
        val questionPair = QuestionMaker.prepareQuestion(points)
        question = questionPair.first
        answerChecker = questionPair.second
    }

    fun start(){
        if(isActive) return

        loadNewQuestion()

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
        lives = 3
        answer = ""
        question = "❔❔❔"
    }


    fun submit(submittedAnswer: String){
        answer = submittedAnswer

        if (answerChecker(answer)){
            points += 1
            loadNewQuestion()
        }
        else{
            lives -= 1
            if(lives == 0){
                addResult(Result(playerName = "Xd", points = points, time = timeMillis))
                reset()
            }
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