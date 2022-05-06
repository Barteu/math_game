import androidx.compose.runtime.*
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.reflect.KProperty
class ResultsManager {

    private val path = "src/json/results.json"
    private var _results: SnapshotStateList<Result> = mutableStateListOf<Result>()
    private var _sortingColumn = 0
    private var _sortingOrder = false
    var results:SnapshotStateList<Result> = mutableStateListOf<Result>()
    var playerText = mutableStateOf("Player")
    var pointsText = mutableStateOf("Points")
    fun getAllResults(): MutableList<Result>{
        try{
            val gson = Gson()
            val results: SnapshotStateList<Result> = gson
                .fromJson(FileReader(path), Array<Result>::class.java)
                .toMutableList()
                .toMutableStateList()
            this._results = results
            sortResults(_sortingColumn, false)
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
        _results.add(result)
        sortResults(_sortingColumn, false)
    }

    fun sortResults(column: Int,changeOrder: Boolean = false){
        if(column == _sortingColumn){
            if (changeOrder)
                _sortingOrder = !_sortingOrder
        }else{
            _sortingOrder = false
            _sortingColumn = column
            this.playerText.value = "Player"
            this.pointsText.value = "Points"
        }
        if (!_sortingOrder) {
            when (_sortingColumn) {
                0 -> {
                    _results.sortBy { it.playerName }
                    this.playerText.value = "Player ↓"
                }
                1 -> {
                    _results.sortBy { it.points }
                    this.pointsText.value = "Points ↓"
                }
            }
        }
        else {
            when (_sortingColumn) {
                0 -> {
                    _results.sortByDescending { it.playerName }
                    this.playerText.value = "Player ↑"
                }
                1 -> {
                    _results.sortByDescending { it.points }
                    this.pointsText.value = "Points ↑"
                }
            }
        }

        results.removeAll(results)
        results.addAll(
                _results
                .slice(0..( if (_results.size <= 9)  _results.size-1 else 9)).toMutableList()
        )

        println(playerText.value)
        println(pointsText.value)
    }


}
