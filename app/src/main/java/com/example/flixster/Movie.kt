import android.os.Parcelable
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var posterPath: String?,
    var title: String?,
    var overview: String?,
    var backdropPath: String?,
    var movieID: Int,
    var rating: Double
) : Parcelable {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("poster_path"),
        jsonObject.getString("title"),
        jsonObject.getString("overview"),
        jsonObject.getString("backdrop_path"),
        jsonObject.getInt("id"),
        jsonObject.getDouble("vote_average")
    )

    companion object {
        @JvmStatic
        @Throws(JSONException::class)
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = ArrayList<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                movies.add(Movie(movieJsonArray.getJSONObject(i)))
            }
            return movies
        }
    }

    fun getPosterPath(): String {
        return "https:"
    }

    fun getBackdropPath(): String {
        return "https:"
    }
}

