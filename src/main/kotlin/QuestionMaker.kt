import kotlin.random.Random

// Extension Function
fun Int .addInt():String{
    if (this >= 0){
        return "+$this"
    }
    else{
        return this.toString()
    }
}

object QuestionMaker {

    val sum = { s: String -> s.split(Regex("(?<=[0-9])(?=[+-])")).map{it.toInt()}.sum().toString() }
    val multiply = { s: String -> s.split(" x ").map{it.toInt()}.fold(1) { acc, next -> acc * next }.toString() }
    val division = {s: String -> s.split(" : ").map{it.toInt()}.reduce{acc, next -> acc/next }.toString() }

    // Higher-Order Function
    fun prepareAnswerChecker(question: String,
                    func: (String) -> String): (String) -> Boolean{
        return    { answer: String -> func(question) == answer}
    }


    fun prepareQuestion(points: Int): Pair<String, (String) -> Boolean> {

        var question = ""

        when (Random.nextInt(0, 3)) {
            0 -> {
                for (i in 1..Random.nextInt(2, 4)) {
                    val number = Random.nextInt(-10 * (1 + points / 5), 11 * (1 + points / 5))
                    question += if(i>1) number.addInt() else number.toString()
                }
                return Pair(question, prepareAnswerChecker(question, sum))
            }
            1 -> {
                for (i in 1..2) {
                    val number = Random.nextInt(-10 * (1 + points / 10), 11 * (1 + points / 10))
                    question += if(i>1) " x $number" else number.toString()
                }
                return Pair(question, prepareAnswerChecker(question, multiply))
            }
            else -> {
                var number = Random.nextInt(2, 11* (1 + points / 10) )
                question = "${number*Random.nextInt(-10* (1 + points / 10) , 11* (1 + points / 10) )} : $number"

                return Pair(question, prepareAnswerChecker(question, division))
            }
        }
    }

}