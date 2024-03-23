package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import com.example.flixster.Movie

class MainActivity : AppCompatActivity() {
    companion object {
        const val NOW_PLAYING_URL = "https:"
        const val TAG = "MainActivity"
    }

    private lateinit var movies: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvMovies: RecyclerView = findViewById(R.id.rvMovies)
        movies = mutableListOf()

        val movieAdapter = MovieAdapter(this, movies)

        rvMovies.adapter = movieAdapter

        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, json: JSONObject?) {
                Log.d(TAG, "onSuccess")
                val jsonObject = json
                try {
                    val results = jsonObject?.getJSONArray("results")
                    Log.i(TAG, "Results" + results.toString())
                    movies.addAll(Movie.fromJsonArray(results ?: JSONArray()))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movies: " + movies.size)
                } catch (e: JSONException) {
                    Log.e(TAG, "Hit JSON exception", e)
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, response: String?, throwable: Throwable?) {
                Log.d(TAG, "onFailure")
            }
        })
    }
}
