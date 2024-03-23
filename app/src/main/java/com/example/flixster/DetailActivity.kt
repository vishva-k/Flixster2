package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException

class DetailActivity : YouTubeBaseActivity() {
    private val YOUTUBE_API_KEY = "AIzaSyCtLaj-pprcgZpMMTVT57m4dBbdu774QVE"
    private val VIDEOS_URL = "https:"
    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        ratingBar = findViewById(R.id.ratingBar)
        youTubePlayerView = findViewById(R.id.player)
        val movie = intent.getParcelableExtra<Movie>("movie")
        tvTitle.text = movie?.title
        tvOverview.text = movie?.overview
        ratingBar.rating = movie?.rating?.toFloat() ?: 0f
        val client = AsyncHttpClient()
        client.get(String.format(VIDEOS_URL, movie?.movieID), object : JsonHttpResponseHandler() {
            override fun onSuccess(i: Int, headers: Array<Header>, json: JSONObject) {
                try {
                    val results = json.getJSONArray("results")
                    if (results.length() == 0) {
                        return
                    }
                    val youtubeKey = results.getJSONObject(0).getString("key")
                    Log.d("DetailActivity", youtubeKey)
                    initializeYouTube(youtubeKey)
                } catch (e: JSONException) {
                    Log.e("DetailActivity", "Failed to parse JSON", e)
                }
            }

            override fun onFailure(i: Int, headers: Array<Header>, s: String, throwable: Throwable) {
            }
        })
    }

    private fun initializeYouTube(youtubeKey: String) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {
                Log.d("DetailActivity", "onInitializationSuccess")
                youTubePlayer.cueVideo(youtubeKey)
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
                Log.d("DetailActivity", "onInitializationFailure")
            }
        })
    }
}

