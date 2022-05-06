import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ResultsManager {

    private val path = "src/json/results.json"
    private var _results = MutableList<Result>(0){ Result() }
    private var _sortingColumn = 0
    private var _sortingOrder = false
    var results = MutableList<Result>(0){Result()}
    var playerText = "Player"
    var pointsText = "Points"
    fun getAllResults(): MutableList<Result>{
        try{
            val gson = Gson()
            val results: MutableList<Result> = gson.fromJson(FileReader(path), Array<Result>::class.java).toMutableList()
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
            playerText = "Player"
            pointsText = "Points"
        }
        if (!_sortingOrder) {
            when (_sortingColumn) {
                0 -> {
                    _results.sortBy { it.playerName }
                    playerText = "Player ↓"
                }
                1 -> {
                    _results.sortBy { it.points }
                    pointsText = "Points ↓"
                }
            }
        }
        else {
            when (_sortingColumn) {
                0 -> {
                    _results.sortByDescending { it.playerName }
                    playerText = "Player ↑"
                }
                1 -> {
                    _results.sortByDescending { it.points }
                    pointsText = "Points ↑"
                }
            }
        }

        results = this._results.slice(0..( if (_results.size <= 9)  _results.size-1 else 9)).toMutableList()
        println(playerText)
        println(pointsText)
    }


}