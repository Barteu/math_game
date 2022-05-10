import ResultsManager.filter
import ResultsManager.path
import ResultsManager.playerText
import ResultsManager.pointsText
import ResultsManager.results
import ResultsManager.resultsAll
import ResultsManager.sortingColumn
import ResultsManager.sortingOrder
import androidx.compose.runtime.*
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.reflect.KProperty
object ResultsManager {
    val path = "src/json/results.json"
    var resultsAll: SnapshotStateList<Result> = mutableStateListOf<Result>()
    var sortingColumn = 0
    var sortingOrder = false
    var results:SnapshotStateList<Result> = mutableStateListOf<Result>()
    var playerText = mutableStateOf("Player")
    var pointsText = mutableStateOf("Points")
    var filter = ""
}

fun getAllResults(): MutableList<Result>{
    try{
        val gson = Gson()
        val results: SnapshotStateList<Result> = gson
            .fromJson(FileReader(path), Array<Result>::class.java)
            .toMutableList()
            .toMutableStateList()
        resultsAll = results
        filterResults(filter)
        return results
    }
    catch(e: Exception) {
        e.printStackTrace()
    }
    return mutableListOf()
}


fun saveAllResults(results: MutableList<Result>){
    try{
        PrintWriter(FileWriter(path)).use{
            val gson = Gson()
            val jsonString = gson.toJson(results)
            it.write(jsonString)
        }
    }
    catch(e: Exception) {
        e.printStackTrace()
    }
}

fun addResult(result: Result){
    resultsAll.add(result)
    filterResults(filter)
}
fun sortResults(column: Int,changeOrder: Boolean = false){
    if(column == sortingColumn){
        if (changeOrder)
            sortingOrder = !sortingOrder
    }else{
        sortingOrder = false
        sortingColumn = column
        playerText.value = "Player"
        pointsText.value = "Points"
    }
    if (!sortingOrder) {
        when (sortingColumn) {
            0 -> {
                results.sortBy { it.playerName }
                playerText.value = "Player ↓"
            }
            1 -> {
                results.sortBy { it.points }
                pointsText.value = "Points ↓"
            }
        }
    }
    else {
        when (sortingColumn) {
            0 -> {
                results.sortByDescending { it.playerName }
                playerText.value = "Player ↑"
            }
            1 -> {
                results.sortByDescending { it.points }
                pointsText.value = "Points ↑"
            }
        }
    }
}

fun filterResults(filterIn: String){
    filter = filterIn
    results.removeAll(results)
    results.addAll(
        resultsAll
            .filter {
                it.playerName
                    .matches(Regex("$filter.*"))
            }
            .let {
                it.slice(0..( if (it.size <= 9)  it.size-1 else 9))
                    .toMutableStateList()
            }
    )
    sortResults(sortingColumn)
}