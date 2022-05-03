import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ResultsManager {

    private val path = "src/json/results.json"
    var results = MutableList<Result>(0){Result()}

    fun getAllResults(): MutableList<Result>{
        try{
            val gson = Gson()
            val results: MutableList<Result> = gson.fromJson(FileReader(path), Array<Result>::class.java).toMutableList()
            results.forEach{
                println(it)
            }
            this.results = results
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


}